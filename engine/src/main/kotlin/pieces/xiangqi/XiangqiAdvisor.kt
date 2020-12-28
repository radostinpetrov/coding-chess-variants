package pieces.xiangqi

import moves.Direction
import moves.Move
import moves.region.CoordinateRegion
import pieces.Piece
import players.Player

data class XiangqiAdvisor(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
            Move.Restricted(Move.Leaper(1, 1), CoordinateRegion(4, 1)),
            Move.Restricted(Move.Leaper(1, 1), CoordinateRegion(4, 8)),
            Move.Restricted(Move.Stepper(Direction.NORTH_EAST, 1, true), CoordinateRegion(3, 7)),
            Move.Restricted(Move.Stepper(Direction.NORTH_WEST, 1, true), CoordinateRegion(5, 7)),
            Move.Restricted(Move.Stepper(Direction.SOUTH_EAST, 1, true), CoordinateRegion(3, 9)),
            Move.Restricted(Move.Stepper(Direction.SOUTH_WEST, 1, true), CoordinateRegion(5, 9)),
            Move.Restricted(Move.Stepper(Direction.SOUTH_EAST, 1, true), CoordinateRegion(3, 2)),
            Move.Restricted(Move.Stepper(Direction.SOUTH_WEST, 1, true), CoordinateRegion(5, 2)),
            Move.Restricted(Move.Stepper(Direction.NORTH_EAST, 1, true), CoordinateRegion(3, 0)),
            Move.Restricted(Move.Stepper(Direction.NORTH_WEST, 1, true), CoordinateRegion(5, 0)),
        )

    override fun getSymbol(): String {
        return "A"
    }
}
