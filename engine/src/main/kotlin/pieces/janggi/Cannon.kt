package pieces.janggi

import moves.Move
import pieces.Piece
import players.Player

class Cannon(override val player: Player) : Piece {
    private val palaceX = listOf(3, 5)
    private val palaceY = listOf(0, 2, 7, 9)

    override val moveTypes: List<Move>
        get() = listOf(
            Move.Hopper(HV = true, canJumpOverSamePiece = false),
            Move.Restricted(Move.Hopper(D = true, canJumpOverSamePiece = false), palaceX, palaceY)
        )

    override fun getSymbol(): String {
        return "C"
    }
}
