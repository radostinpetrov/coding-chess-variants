package pieces

import moves.Move
import moves.Move.*
import moves.Direction
import players.Player

class WhitePawn(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
            Stepper(Direction.NORTH, 1),
            Restricted(Stepper(Direction.NORTH, 2), listOf(), listOf(1)),
            CaptureOnly(Stepper(Direction.NORTH_EAST, 1, true)),
            CaptureOnly(Stepper(Direction.NORTH_WEST, 1, true)),
        )

    override fun getSymbol(): String {
        return "P"
    }
}