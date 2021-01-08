package pieces.chess

import moveGenerators.MoveGenerator2D
import pieces.Piece2D
import players.Player

/**
 * Represents a marshal, which is a combination of a knight and a rook
 */
data class Marshal(override val player: Player) : Piece2D {
    override val moveGenerators =
        listOf(MoveGenerator2D.Leaper(2, 1), MoveGenerator2D.Slider(H = true, V = true, A = false, D = false))

    override fun getSymbol(): String {
        return "M"
    }
}
