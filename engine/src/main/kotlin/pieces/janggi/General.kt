package pieces.janggi

import coordinates.Coordinate2D
import moveGenerators.Direction
import moveGenerators.MoveGenerator2D
import regions.BoxRegion
import regions.CoordinateRegion
import regions.CompositeRegion
import pieces.Royal
import pieces.Piece2D
import players.Player

data class General(override val player: Player) : Piece2D, Royal {
    private val palace1 = BoxRegion(Coordinate2D(3, 0), Coordinate2D(5, 2))
    private val palace2 = BoxRegion(Coordinate2D(3, 7), Coordinate2D(5, 9))
    private val palace = CompositeRegion(listOf(palace1, palace2))

    override val moveGenerators: List<MoveGenerator2D>
        get() = listOf(
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Stepper(Direction.NORTH, 1, true), palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Stepper(Direction.EAST, 1, true), palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Stepper(Direction.SOUTH, 1, true), palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Stepper(Direction.WEST, 1, true), palace),
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
            MoveGenerator2D.Skip,
        )

    override fun getSymbol(): String {
        return "K"
    }
}
