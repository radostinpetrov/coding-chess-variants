package pieces.chess

import moveGenerators.MoveGenerator2D.Slider
import pieces.Piece2D
import players.Player

/**
 * Represents a bishop in standard chess
 */
data class Bishop(override val player: Player) : Piece2D {
    override val moveGenerators = listOf(Slider(A = true, D = true))

    override fun getSymbol(): String {
        return "B"
    }
}
