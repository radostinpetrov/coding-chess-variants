package pieces.xiangqi

import moves.Direction
import moves.Move2D
import moves.region.CoordinateRegion
import pieces.Piece
import pieces.Piece2D
import players.Player

data class XiangqiAdvisor(override val player: Player) : Piece2D {
    override val moveTypes: List<Move2D>
        get() = listOf(
            Move2D.Restricted(Move2D.Leaper(1, 1), CoordinateRegion(4, 1)),
            Move2D.Restricted(Move2D.Leaper(1, 1), CoordinateRegion(4, 8)),
            Move2D.Restricted(Move2D.Stepper(Direction.NORTH_EAST, 1, true), CoordinateRegion(3, 7)),
            Move2D.Restricted(Move2D.Stepper(Direction.NORTH_WEST, 1, true), CoordinateRegion(5, 7)),
            Move2D.Restricted(Move2D.Stepper(Direction.SOUTH_EAST, 1, true), CoordinateRegion(3, 9)),
            Move2D.Restricted(Move2D.Stepper(Direction.SOUTH_WEST, 1, true), CoordinateRegion(5, 9)),
            Move2D.Restricted(Move2D.Stepper(Direction.SOUTH_EAST, 1, true), CoordinateRegion(3, 2)),
            Move2D.Restricted(Move2D.Stepper(Direction.SOUTH_WEST, 1, true), CoordinateRegion(5, 2)),
            Move2D.Restricted(Move2D.Stepper(Direction.NORTH_EAST, 1, true), CoordinateRegion(3, 0)),
            Move2D.Restricted(Move2D.Stepper(Direction.NORTH_WEST, 1, true), CoordinateRegion(5, 0)),
        )

    override fun getSymbol(): String {
        return "A"
    }
}
