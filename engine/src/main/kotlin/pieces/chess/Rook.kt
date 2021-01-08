package pieces.chess

import moveGenerators.MoveGenerator2D
import pieces.Piece2D
import players.Player

data class Rook(override val player: Player) : Piece2D {
    override val moveGenerators = listOf(MoveGenerator2D.Slider(H = true, V = true, A = false, D = false))

    override fun getSymbol(): String {
        return "R"
    }
}
