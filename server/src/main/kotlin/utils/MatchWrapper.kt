package server.utils

import gameTypes.GameType2D

data class MatchWrapper(val myUsername: String,
                        val opponentUsername: String,
                        val game: GameType2D,
                        val myPlayerIndex: Int)