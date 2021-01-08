package pieces.janggi

import moveGenerators.MoveGenerator2D
import pieces.Piece2D
import players.Player

/**
 * Represents a cannon in Janggi
 *
 *
 */
data class Cannon(override val  player: Player) : Piece2D {
    override val moveGenerators =
        listOf(
            MoveGenerator2D.Hopper(HV = true, canJumpOverSamePiece = false),
            MoveGenerator2D.Restricted(MoveGenerator2D.Hopper(D = true, canJumpOverSamePiece = false), SpecialRegion.palace)
        )

    override fun getSymbol(): String {
        return "C"
    }
}
