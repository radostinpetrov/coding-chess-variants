package main.kotlin.pieces

import main.kotlin.moves.Move
import main.kotlin.players.Player

interface Piece {
    val moveTypes: List<Move>
    val player: Player
    fun getSymbol(): String
}
