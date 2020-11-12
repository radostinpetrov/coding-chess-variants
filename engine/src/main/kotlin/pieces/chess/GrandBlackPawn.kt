package main.kotlin.pieces.chess

import main.kotlin.players.Player

data class GrandBlackPawn(override val player: Player) : BlackPawn(player, 7)
