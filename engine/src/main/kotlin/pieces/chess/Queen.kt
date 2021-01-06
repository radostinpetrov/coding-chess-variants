package pieces.chess

import moveGenerators.MoveGenerator2D
import pieces.Piece2D
import players.Player

data class Queen(override val player: Player) : Piece2D {
    override val moveGenerators: List<MoveGenerator2D>
        get() = listOf(MoveGenerator2D.Slider(H = true, V = true, A = true, D = true))

    override fun getSymbol(): String {
        return "Q"
    }
}
