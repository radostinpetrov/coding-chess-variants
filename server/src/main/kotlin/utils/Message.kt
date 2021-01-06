package server.utils

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val username: String? = null,
    val type: String,
    val gameMode: String? = null,
    val clockOption: String? = null,
    val opponentId: String? = null,
    val move: Int? = null,
    val gameResult: Double? = null
)
