package pieces.xiangqi

import coordinates.Coordinate2D
import moveGenerators.Direction
import moveGenerators.MoveGenerator2D
import regions.BoxRegion
import pieces.Piece2D
import players.Player

data class XiangqiBlueElephant(override val player: Player) : Piece2D {
    private val river = BoxRegion(Coordinate2D(0, 5), Coordinate2D(8, 9))

    override val moveGenerators =
        listOf(
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Stepper(Direction.NORTH_EAST, 2, true), river),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Stepper(Direction.NORTH_WEST, 2, true), river),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Stepper(Direction.SOUTH_EAST, 2, true), river),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Stepper(Direction.SOUTH_WEST, 2, true), river),
        )

    override fun getSymbol(): String {
        return "B"
    }
}
