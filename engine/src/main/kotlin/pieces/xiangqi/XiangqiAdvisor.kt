package pieces.xiangqi

import moveGenerators.Direction
import moveGenerators.MoveGenerator2D
import regions.CoordinateRegion
import pieces.Piece2D
import players.Player

data class XiangqiAdvisor(override val player: Player) : Piece2D {
    override val moveGenerators =
        listOf(
            MoveGenerator2D.Restricted(MoveGenerator2D.Leaper(1, 1), CoordinateRegion(4, 1)),
            MoveGenerator2D.Restricted(MoveGenerator2D.Leaper(1, 1), CoordinateRegion(4, 8)),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.NORTH_EAST, 1, true), CoordinateRegion(3, 7)),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.NORTH_WEST, 1, true), CoordinateRegion(5, 7)),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.SOUTH_EAST, 1, true), CoordinateRegion(3, 9)),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.SOUTH_WEST, 1, true), CoordinateRegion(5, 9)),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.SOUTH_EAST, 1, true), CoordinateRegion(3, 2)),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.SOUTH_WEST, 1, true), CoordinateRegion(5, 2)),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.NORTH_EAST, 1, true), CoordinateRegion(3, 0)),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.NORTH_WEST, 1, true), CoordinateRegion(5, 0)),
        )

    override fun getSymbol(): String {
        return "A"
    }
}
