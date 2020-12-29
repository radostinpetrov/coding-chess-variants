package pieces.xiangqi

import coordinates.Coordinate2D
import moves.Direction
import moves.Move2D
import moves.region.BoxRegion
import pieces.Piece2D
import players.Player

data class XiangqiRedElephant(override val player: Player) : Piece2D {
    private val river = BoxRegion(Coordinate2D(0, 0), Coordinate2D(9, 4))

    override val moveTypes: List<Move2D>
        get() = listOf(
            Move2D.RestrictedDestination(Move2D.Stepper(Direction.NORTH_EAST, 2, true), river),
            Move2D.RestrictedDestination(Move2D.Stepper(Direction.NORTH_WEST, 2, true), river),
            Move2D.RestrictedDestination(Move2D.Stepper(Direction.SOUTH_EAST, 2, true), river),
            Move2D.RestrictedDestination(Move2D.Stepper(Direction.SOUTH_WEST, 2, true), river),
        )

    override fun getSymbol(): String {
        return "B"
    }
}
