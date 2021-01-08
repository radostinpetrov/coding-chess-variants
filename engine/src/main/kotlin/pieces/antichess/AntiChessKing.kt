package pieces.antichess

import moveGenerators.MoveGenerator2D
import pieces.Piece2D
import players.Player

/**
 * Represents a king of anti chess
 */
data class AntiChessKing(override val player: Player) : Piece2D {
    override val moveGenerators = listOf(MoveGenerator2D.Leaper(1, 0), MoveGenerator2D.Leaper(1, 1))

    override fun getSymbol(): String {
        return "K"
    }
}
