package main.kotlin.pieces.chess

import main.kotlin.moves.Move
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

data class Cardinal(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(Move.Leaper(2, 1), Move.Slider(A = true, D = true))

    override fun getSymbol(): String {
        return "C"
    }
}
