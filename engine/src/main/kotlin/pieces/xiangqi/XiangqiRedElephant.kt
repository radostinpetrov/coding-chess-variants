package pieces.xiangqi

import moves.Direction
import moves.Move
import pieces.Piece
import players.Player

class XiangqiRedElephant(override val player: Player) : Piece {
    private val river = listOf(0, 1, 2, 3, 4)

    override val moveTypes: List<Move>
        get() = listOf(
            Move.RestrictedDestination(Move.Stepper(Direction.NORTH_EAST, 2, true), listOf(), river),
            Move.RestrictedDestination(Move.Stepper(Direction.NORTH_WEST, 2, true), listOf(), river),
            Move.RestrictedDestination(Move.Stepper(Direction.SOUTH_EAST, 2, true), listOf(), river),
            Move.RestrictedDestination(Move.Stepper(Direction.SOUTH_WEST, 2, true), listOf(), river),
        )

    override fun getSymbol(): String {
        return "B"
    }
}
