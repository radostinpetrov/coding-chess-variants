package server.utils

import gameTypes.GameType2D
import java.util.*

data class MatchWrapper(val myUsername: String,
                        val opponentUsername: String,
                        val opponentId: UUID,
                        val game: GameType2D,
                        val myPlayerIndex: Int)