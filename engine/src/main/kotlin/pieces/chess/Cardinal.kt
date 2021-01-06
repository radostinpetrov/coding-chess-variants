package pieces.chess

import moveGenerators.MoveGenerator2D
import pieces.Piece2D
import players.Player

data class Cardinal(override val player: Player) : Piece2D {
    override val moveGenerators: List<MoveGenerator2D>
        get() = listOf(MoveGenerator2D.Leaper(2, 1), MoveGenerator2D.Slider(A = true, D = true))

    override fun getSymbol(): String {
        return "C"
    }
}
