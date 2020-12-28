package pieces.xiangqi

import Coordinate
import moves.Direction
import moves.Move
import moves.region.BoxRegion
import pieces.Piece
import players.Player

data class XiangqiBlueElephant(override val player: Player) : Piece {
    private val river = BoxRegion(Coordinate(0, 0), Coordinate(9, 5))

    override val moveTypes: List<Move>
        get() = listOf(
            Move.RestrictedDestination(Move.Stepper(Direction.NORTH_EAST, 2, true), river),
            Move.RestrictedDestination(Move.Stepper(Direction.NORTH_WEST, 2, true), river),
            Move.RestrictedDestination(Move.Stepper(Direction.SOUTH_EAST, 2, true), river),
            Move.RestrictedDestination(Move.Stepper(Direction.SOUTH_WEST, 2, true), river),
        )

    override fun getSymbol(): String {
        return "B"
    }
}
