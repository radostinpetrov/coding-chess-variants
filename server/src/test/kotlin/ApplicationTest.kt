package server

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import server.utils.Message
import java.time.*
import kotlin.test.*

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
