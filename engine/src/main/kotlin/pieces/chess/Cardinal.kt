package pieces.chess

import moves.Move2D
import pieces.Piece2D
import players.Player

data class Cardinal(override val player: Player) : Piece2D {
    override val moveTypes: List<Move2D>
        get() = listOf(Move2D.Leaper(2, 1), Move2D.Slider(A = true, D = true))

    override fun getSymbol(): String {
        return "C"
    }
}
