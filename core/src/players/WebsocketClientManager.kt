package players

import gameMoves.GameMove2D
import gameTypes.chess.AbstractChess
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONObject
import java.net.URI

class WebsocketClientManager(val startGameFunction: (Int, Double) -> Unit, var username: String, val gameName: String?, val clockOption: String) {
    lateinit var game: AbstractChess
    lateinit var networkHumanPlayer: NetworkHumanPlayer
    lateinit var networkEnemyPlayer: NetworkEnemyPlayer

    val serverUri: URI = URI("ws://207.246.87.201:8080")
    private var turnMove: GameMove2D? = null

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
//                println("onMessage")
//                println(message)
                val jsonMessage = JSONObject(message)
                when (jsonMessage.getString("type")) {
                    "startGame" -> {
                        // we need to do something with the screen here we pass in the change to game screen function
                        // but we need to pass in this class as well and set networkPlayer 1 and 2
//                        networkHumanPlayer = NetworkHumanPlayer()
//                        networkEnemyPlayer = game.
                        enemyId = jsonMessage.getString("opponentId")
                        startGameFunction(jsonMessage.getInt("player"), jsonMessage.getDouble("seed"))
                    }
                    "receiveMove" -> {
                        val move = jsonMessage.getInt("move")
                        networkEnemyPlayer.makeMove(move)
                    }
                }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                println("onClose")
//            Log.d(TAG, "onClose")
//                unsubscribe()
            }

            override fun onError(ex: Exception?) {
                println(ex?.message)
                println("onError")
//            Log.e(TAG, "onError: ${ex?.message}")
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

    private fun sendMatchmakingRequest() {
        webSocketClient.send(
            "{\n" +
                "    \"type\": \"matchmaking\",\n" +
                "    \"gameMode\": \"standard\",\n" +
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

//    private fun unsubscribe() {
//        webSocketClient.send(
//                "{\n" +
//                        "    \"type\": \"unsubscribe\",\n" +
//                        "    \"channels\": [\"ticker\"]\n" +
//                        "}"
//        )
//    }

    fun setTurnMove(move: GameMove2D) {
        turnMove = move
    }

//    override fun getTurn(choiceOfMoves: List<gameMoves.GameMove>): gameMoves.GameMove? {
//        if (turnMove) {
//
//        }
//
//
//        return null
//    }
}
