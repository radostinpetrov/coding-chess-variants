package server.utils

import io.ktor.websocket.*
import server.models.Players
import java.util.*

data class PlayerWrapper(val ws: DefaultWebSocketServerSession, val uuid: UUID, val username: String)