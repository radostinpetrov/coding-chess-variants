package pieces.chess

import moveGenerators.MoveGenerator2D
import pieces.Piece2D
import pieces.Royal
import players.Player

/**
 * Represents a king in standard chess
 */
data class King(override val player: Player) : Piece2D, Royal {
    override val moveGenerators = listOf(MoveGenerator2D.Leaper(1, 0), MoveGenerator2D.Leaper(1, 1))

    override fun getSymbol(): String {
        return "K"
    }
}
