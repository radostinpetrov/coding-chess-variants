package players

import gameTypes.chess.AbstractChess
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONObject
import screens.GameScreen
import java.net.URI

class WebsocketClientManager(val startGameFunction: (JSONObject) -> Unit, var username: String, val gameName: String?, val clockOption: String) {
    lateinit var game: AbstractChess
    lateinit var networkHumanPlayer: NetworkHumanPlayer
    lateinit var networkEnemyPlayer: NetworkEnemyPlayer
    lateinit var gameScreen: GameScreen

    val serverUri: URI = URI("ws://83.136.252.48:8080")

    private lateinit var webSocketClient: WebSocketClient

    lateinit var enemyId: String

    init {
        if (username == "") {
            username = "Guest"
        }
        println(username)
        createWebSocketClient(serverUri)

//        val socketFactory: SSLSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
//        webSocketClient.setSocketFactory(socketFactory)
        webSocketClient.connect()
    }

    private fun createWebSocketClient(uri: URI?) {
        webSocketClient = object : WebSocketClient(uri) {

            override fun onOpen(handshakedata: ServerHandshake?) {
                println("open")
                sendMatchmakingRequest()
            }

            override fun onMessage(message: String?) {
                val jsonMessage = JSONObject(message)
                when (jsonMessage.getString("type")) {
                    "startGame" -> {
                        // we need to do something with the screen here we pass in the change to game screen function
                        // but we need to pass in this class as well and set networkPlayer 1 and 2
                        enemyId = jsonMessage.getString("opponentId")
                        startGameFunction(jsonMessage)
                    }
                    "receiveMove" -> {
                        val move = jsonMessage.getInt("move")
                        networkEnemyPlayer.makeMove(move)
                    }
                    "opponentConcede" -> {
                        networkEnemyPlayer.concede()
                    }
                    "timeoutWin" -> {
                        val winnerIndex = jsonMessage.getInt("winnerIndex")
                        val winner = gameScreen.gameEngine.players[winnerIndex]
                        gameScreen.processTimeoutWin(winner)
                    }
                }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                println("onClose")
            }

            override fun onError(ex: Exception?) {
                println(ex?.message)
                println("onError")
            }
        }
    }

    fun sendPlayerMove(moveIndex: Int) {
        webSocketClient.send(
            "{\n" +
                "    \"type\": \"makeMove\",\n" +
                "    \"move\": $moveIndex,\n" +
                "    \"opponentId\": \"$enemyId\"\n" +
                "}"
        )
    }

    fun sendConcede() {
        webSocketClient.send(
            "{\n" +
                    "    \"type\": \"concede\"\n" +
                    "}"
        )
    }

    private fun sendMatchmakingRequest() {
        webSocketClient.send(
            "{\n" +
                "    \"type\": \"matchmaking\",\n" +
                "    \"gameMode\": \"$gameName\",\n" +
                "    \"clockOption\": \"$clockOption\",\n" +
                "    \"username\": \"$username\"" +
                "}"
        )
    }

    fun sendResult(gameResult: Float) {
        webSocketClient.send(
            "{\n" +
                "    \"type\": \"finishGame\",\n" +
                "    \"gameResult\": $gameResult" +
                "}"
        )
    }
}
