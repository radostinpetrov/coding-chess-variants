package main.kotlin.pieces.chess

import main.kotlin.pieces.chess.WhitePawn
import main.kotlin.players.Player

data class StandardWhitePawn(override val player: Player) : WhitePawn(player, 1)
