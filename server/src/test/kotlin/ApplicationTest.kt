package server

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.websocket.*
import io.ktor.http.cio.websocket.*
import java.time.*
import kotlin.test.*
import io.ktor.server.testing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import server.utils.Message

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Server is running!", response.content)
            }
        }
    }

    @Test
    fun testConversation() {
        withTestApplication({ module(testing = true) }) {
            handleWebSocketConversation("") { incoming, outgoing ->
                val msg = Json.encodeToString(Message(type = "getLeaderboard"))
                outgoing.send(Frame.Text(msg))
                println((incoming.receive() as Frame.Text).readText())

            }
        }
    }
}
