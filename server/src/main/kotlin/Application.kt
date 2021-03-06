package server

import endconditions.Outcome
import com.mongodb.MongoWriteException
import com.mongodb.client.MongoCollection
import gameTypes.checkers.Checkers
import gameTypes.chess.*
import gameTypes.hex.HexagonalChess
import gameTypes.xiangqi.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.litote.kmongo.*
import server.models.Players
import server.utils.*
import java.time.*
import java.util.UUID
import kotlin.math.roundToInt
import kotlin.random.Random

val jsonFormat = Json { encodeDefaults = true }

val matchMakingQueues: MutableMap<GameQueue, MutableList<PlayerWrapper>> = mutableMapOf()
val matches: MutableMap<UUID, MatchWrapper<*,*,*,*,*>> = mutableMapOf()
val players: MutableMap<UUID, DefaultWebSocketServerSession> = mutableMapOf()

lateinit var col: MongoCollection<Players>
lateinit var appContext: Application

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    // Initialization

//    val dotenv = dotenv()
//    val mongoUri = dotenv["DB_CONNECTION"]
    val mongoClient = KMongo.createClient("mongodb+srv://admin:BHI06RpWO2qFGpd3@chess-cluster-1.uzp54.mongodb.net/chess?retryWrites=true&w=majority")
    val database = mongoClient.getDatabase("chess")
    col = database.getCollection()
    appContext = this

    install(io.ktor.websocket.WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    routing {
        get("/") {
            call.respondText("Server is running!", contentType = ContentType.Text.Plain)
        }

        webSocket("/") {
            val uuid = UUID.randomUUID()
            players[uuid] = this

            try {
                while (true) {
                    val frame = incoming.receive()
                    if (frame !is Frame.Text) {
                        continue
                    }

                    val text = frame.readText()
                    println("onMessage")

                    val msg = jsonFormat.decodeFromString<Message>(text)

                    if (msg.username != null) {
                        val player = col.findOne(Players::username eq msg.username)
                        if (player == null) {
                            val newPlayer = Players(username = msg.username)
                            try {
                                col.insertOne(newPlayer)
                            } catch (e: MongoWriteException) {
                                // If duplicated key (error code 11000), we know that the player's username already exists
                                if (e.error.code != 11000) {
                                    println(e.error.message)
                                }
                            }
                        }
                    }

                    when (msg.type) {
                        "matchmaking" -> matchmaking(this, uuid, msg)
                        "makeMove" -> makeMove(uuid, msg)
                        "getLeaderboard" -> getLeaderboard(this)
                        "concede" -> concedeGame(uuid)
//                        "finishGame" -> finishGame(this, uuid, msg)
                    }
                }
            } catch (e: ClosedReceiveChannelException) {
                println("onClose ${closeReason.await()}")
            } catch (e: Throwable) {
                println("onError ${closeReason.await()}")
                e.printStackTrace()
            }
            println("onClose or onError")
            removeUser(uuid)
        }
    }
}

suspend fun removeUser(uuid: UUID) {
    for (kv in matchMakingQueues) {
        val queue = kv.value
        queue.removeAll { e -> e.uuid == uuid }
    }

    concedeGame(uuid)

    players.remove(uuid)
}

suspend fun concedeGame(uuid: UUID) {
    val match = matches[uuid]
    if (match != null) {
        matches.remove(uuid)
        matches.remove(match.opponentId)

        @Serializable
        data class ConcedeMessage(val type: String = "opponentConcede")

        players[match.opponentId]?.send(jsonFormat.encodeToString(ConcedeMessage()))

        updateElo(match.myUsername, 0.0, match.opponentUsername, 1.0)
    }
}

// fun finishGame(outcome: winconditions.Outcome, game: GameType2D, winner: String, loser: String, msg: Message) {
//    val match = matches[uuid]
//
//    if (match == null) {
//        println("Cannot find usernames associated to match")
//        return
//    }
//
//    val myUsername = match.myUsername
//    val opponentUsername = match.opponentUsername
//
//    val myPlayer = col.findOne(Players::username eq myUsername)
//    val opponentPlayer = col.findOne(Players::username eq opponentUsername)
//
//    if (myPlayer == null || opponentPlayer == null) {
//        println("Error: Missing player information")
//        return
//    }
//
//    if (msg.gameResult == null) {
//        println("Error: Missing game result information")
//        return
//    }
//
//    val myNewElo = getNewRating(myPlayer.elo, opponentPlayer.elo, msg.gameResult)
//    val opponentNewElo = getNewRating(opponentPlayer.elo, myPlayer.elo, 1.0 - msg.gameResult)
//
//    col.updateOne(Players::username eq myUsername, setValue(Players::elo, myNewElo))
//    col.updateOne(Players::username eq opponentUsername, setValue(Players::elo, opponentNewElo))
// }

fun getNewRating(myRating: Int, opponentRating: Int, gameResult: Double): Int {
    return myRating + getRatingDelta(myRating, opponentRating, gameResult)
}

fun getRatingDelta(myRating: Int, opponentRating: Int, gameResult: Double): Int {
    val myChanceToWin = 1.0 / (1.0 + Math.pow(10.0, ((opponentRating - myRating).toDouble() / 400.0)))

    return (32 * (gameResult - myChanceToWin)).roundToInt()
}

suspend fun getLeaderboard(ws: DefaultWebSocketServerSession) {
    val sortedPlayers = col.find().toList().sortedDescending()

    @Serializable
    data class LeaderboardMessage(
        val type: String = "receiveLeaderboard",
        val error: String? = null,
        val players: List<Players>
    )

    val msgToSend = LeaderboardMessage(players = sortedPlayers)

    ws.send(jsonFormat.encodeToString(msgToSend))
}

suspend fun makeMove(uuid: UUID, msg: Message) {
    if (msg.move == null) {
        println("Missing move")
        return
    }

    val match = matches[uuid]
    if (match == null) {
        println("Error: Player is not in a match")
        return
    }

    val opponentId = match.opponentId

    val game = match.game

    match.makeValidMove(msg.move)

    val opponentMatch = matches[match.opponentId]
    if (opponentMatch == null) {
        println("Error: Opponent player is not in a match")
        return
    }

    // Send move to opponent player
    @Serializable
    data class MakeMoveMessage(
        val type: String,
        val move: Int,
        val opponentId: String,
        val myTime: Long?,
        val opponentTime: Long?
    )

    // Process timer logic
    cancelAndUpdateTimer(uuid)

    val msgToSend = MakeMoveMessage(
        "receiveMove", msg.move, opponentId.toString(), opponentMatch.myRemainingTime, match.myRemainingTime
    )
    val wsDest = players[opponentId]
    if (wsDest == null) {
        println("Found invalid UUID")
        return
    }

    wsDest.send(jsonFormat.encodeToString(msgToSend))

    val myPlayer = game.getCurrentPlayer()

    // Check if game is finished
    val outcome = game.getOutcome()
    if (outcome != null) {
        val myUsername = match.myUsername
        val opponentUsername = match.opponentUsername
        when (outcome) {
            is Outcome.Win -> {
                if (myPlayer == outcome.winner) {
                    updateElo(myUsername, 1.0, opponentUsername, 0.0)
                } else {
                    updateElo(myUsername, 0.0, opponentUsername, 1.0)
                }
            }
            is Outcome.Draw -> {
                updateElo(myUsername, 0.5, opponentUsername, 0.5)
            }
        }
        matches.remove(uuid)
        matches.remove(opponentId)
    } else {
        // If game is not finished
        launchTimer(opponentId)
    }
}

suspend fun launchTimer(uuid: UUID) {
    val match = matches[uuid] ?: return
    val remainingTime = match.myRemainingTime ?: return
    match.myTimerJob = appContext.launch {
        match.stopWatch.reset()
        match.stopWatch.start()

        delay(remainingTime)
        timeoutVictory(match.opponentId)
    }
}

suspend fun cancelAndUpdateTimer(uuid: UUID) {
    val match = matches[uuid] ?: return
    val remainingTime = match.myRemainingTime ?: return
    val myTimerJob = match.myTimerJob ?: return

    if (myTimerJob.isActive) {
        myTimerJob.cancel()
    }

    match.stopWatch.stop()
    val newRemainingTime = remainingTime - match.stopWatch.time

    if (newRemainingTime <= 0) {
        timeoutVictory(match.opponentId)
        return
    }

    match.myRemainingTime = newRemainingTime
    match.myTimerJob = null
    match.stopWatch.reset()
}

suspend fun timeoutVictory(winnerUuid: UUID) {
    val match = matches[winnerUuid]
    if (match != null) {
        matches.remove(winnerUuid)
        matches.remove(match.opponentId)

        @Serializable
        data class TimeoutWinMessage(val type: String = "timeoutWin", val winnerIndex: Int)

        players[winnerUuid]?.send(
            jsonFormat.encodeToString(TimeoutWinMessage(winnerIndex = match.myPlayerIndex))
        )
        players[match.opponentId]?.send(
            jsonFormat.encodeToString(TimeoutWinMessage(winnerIndex = match.myPlayerIndex))
        )

        updateElo(match.myUsername, 1.0, match.opponentUsername, 0.0)
    }
}

fun updateElo(myUsername: String, myResult: Double, opponentUsername: String, opponentResult: Double) {
    if (myUsername == opponentUsername) {
        return
    }
    val myPlayer = col.findOne(Players::username eq myUsername)
    val opponentPlayer = col.findOne(Players::username eq opponentUsername)

    if (myPlayer == null || opponentPlayer == null) {
        println("Error: Missing player information")
        return
    }

    val myNewElo = getNewRating(myPlayer.elo, opponentPlayer.elo, myResult)
    val opponentNewElo = getNewRating(opponentPlayer.elo, myPlayer.elo, opponentResult)

    col.updateOne(Players::username eq myUsername, setValue(Players::elo, myNewElo))
    col.updateOne(Players::username eq opponentUsername, setValue(Players::elo, opponentNewElo))
}

suspend fun matchmaking(ws: DefaultWebSocketServerSession, uuid: UUID, msg: Message) {
    if (msg.gameMode == null || msg.username == null) {
        return
    }

    lateinit var pw1: PlayerWrapper
    lateinit var pw2: PlayerWrapper
    var start = false

    val gameQueue = GameQueue(msg.gameMode, msg.clockOption)

    matchMakingQueues.putIfAbsent(gameQueue, mutableListOf())

    val queue = matchMakingQueues[gameQueue]!!

    queue.add(PlayerWrapper(ws, uuid, msg.username))
    if (queue.size == 2) {
        pw1 = queue[0]
        pw2 = queue[1]
        queue.clear()
        start = true
    }

    if (!start) {
        return
    }

    val seed = Random.nextDouble()

    @Serializable
    data class StartGameMessage(
        val type: String,
        val player: Int,
        val opponentId: String,
        val playerUsername: String,
        val opponentUsername: String,
        val playerElo: Int,
        val opponentElo: Int,
        val seed: Double
    )

    val game = when (msg.gameMode) {
        "StandardChess" -> StandardChess()
        "GrandChess" -> GrandChess()
        "CapablancaChess" -> CapablancaChess()
        "Chess960" -> Chess960()
        "Janggi" -> Janggi()
        "Xiangqi" -> Xiangqi()
        "AntiChess" -> AntiChess()
        "MiniChess" -> MiniChess()
        "Checkers" -> Checkers()
        "HexagonalChess" -> HexagonalChess()
        "BalbosGame" -> BalbosGame()
        else -> null
    }

    if (game == null) {
        println("Error: Invalid game mode")
        return
    }

    game.initGame()

    val initialRemainingTime = msg.clockOption?.toLongOrNull()?.times(1000L)

    val pw1Player = col.findOne(Players::username eq pw1.username)
    val pw2Player = col.findOne(Players::username eq pw2.username)

    if (pw1Player == null || pw2Player == null) {
        println("Error: Missing player information")
        return
    }

    matches[pw1.uuid] = MatchWrapper(pw1.username, pw2.username, pw2.uuid, game, 0, initialRemainingTime)
    matches[pw2.uuid] = MatchWrapper(pw2.username, pw1.username, pw1.uuid, game, 1, initialRemainingTime)

    pw1.ws.send(jsonFormat.encodeToString(StartGameMessage(
        "startGame", 1, pw2.uuid.toString(), pw1.username, pw2.username, pw1Player.elo, pw2Player.elo, seed))
    )
    pw2.ws.send(jsonFormat.encodeToString(StartGameMessage(
        "startGame", 2, pw1.uuid.toString(), pw2.username, pw1.username, pw2Player.elo, pw1Player.elo, seed))
    )

    // Start the timer
    launchTimer(pw1.uuid)
}
