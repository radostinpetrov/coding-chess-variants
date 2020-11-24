package pieces.chess

import moves.Move
import pieces.Piece
import players.Player

data class Cardinal(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(Move.Leaper(2, 1), Move.Slider(A = true, D = true))

    override fun getSymbol(): String {
        return "C"
    }
}
