package pieces.janggi

import moves.Direction
import moves.Move
import pieces.Piece
import players.Player

class RedSoldier(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
            Move.Stepper(Direction.EAST, 1, true),
            Move.Stepper(Direction.WEST, 1, true),
            Move.Stepper(Direction.NORTH, 1, true),
            Move.Restricted(Move.Stepper(Direction.NORTH_EAST, 1, true), listOf(3), listOf(7)),
            Move.Restricted(Move.Stepper(Direction.NORTH_EAST, 1, true), listOf(4), listOf(8)),
            Move.Restricted(Move.Stepper(Direction.NORTH_WEST, 1, true), listOf(5), listOf(7)),
            Move.Restricted(Move.Stepper(Direction.NORTH_WEST, 1, true), listOf(4), listOf(8)),
        )

    override fun getSymbol(): String {
        return "P"
    }
}
