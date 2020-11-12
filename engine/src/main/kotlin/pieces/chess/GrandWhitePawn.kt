package main.kotlin.pieces.chess

import main.kotlin.players.Player

data class GrandWhitePawn(override val player: Player) : WhitePawn(player, 2)
