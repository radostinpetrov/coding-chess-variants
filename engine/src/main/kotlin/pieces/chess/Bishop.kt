package pieces.chess

import moves.Move2D
import moves.Move2D.Slider
import pieces.Piece2D
import players.Player

data class Bishop(override val player: Player) : Piece2D {
    override val moveTypes: List<Move2D>
        get() = listOf(Slider(A = true, D = true))

    override fun getSymbol(): String {
        return "B"
    }
}
