package main.kotlin.pieces.chess

import main.kotlin.players.Player

data class StandardBlackPawn(override val player: Player) : BlackPawn(player, 6)