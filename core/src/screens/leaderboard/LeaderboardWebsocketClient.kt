package screens.leaderboard

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONObject
import java.net.URI

class LeaderboardWebsocketClient(val leaderboardScreen: LeaderboardScreen) {
    val serverUri: URI = URI("ws://83.136.252.48:8080")

    private lateinit var webSocketClient: WebSocketClient

    init {
        createWebSocketClient(serverUri)
//        val socketFactory: SSLSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
//        webSocketClient.setSocketFactory(socketFactory)
        webSocketClient.connect()
    }

    private fun createWebSocketClient(uri: URI?) {
        webSocketClient = object : WebSocketClient(uri) {

            override fun onOpen(handshakedata: ServerHandshake?) {
                println("leaderboardSocket: open")
                getLeaderboard()
            }

            override fun onMessage(message: String?) {
//                println("onMessage")
//                println(message)
                val jsonMessage = JSONObject(message)
                when (jsonMessage.getString("type")) {
                    "receiveLeaderboard" -> {
                        val leaderboardArr = jsonMessage.getJSONArray("players")
                        leaderboardScreen.loadLeaderboard(leaderboardArr)
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

    fun getLeaderboard() {
        webSocketClient.send(
            "{\n" +
                "    \"type\": \"getLeaderboard\"\n" +
                "}"
        )
    }
}
