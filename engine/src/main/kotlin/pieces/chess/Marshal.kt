package main.kotlin.pieces.chess

import main.kotlin.players.Player
import main.kotlin.moves.Move
import main.kotlin.pieces.Piece

data class Marshal(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(Move.Leaper(2, 1), Move.Slider(H = true, V = true, A = false, D = false))

    override fun getSymbol(): String {
        return "M"
    }
}
