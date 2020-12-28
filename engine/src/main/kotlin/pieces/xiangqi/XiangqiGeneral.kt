package pieces.xiangqi

import Coordinate
import moves.Direction
import moves.Move
import moves.region.BoxRegion
import moves.region.CompositeRegion
import moves.region.CoordinateRegion
import pieces.King
import pieces.Piece
import players.Player

data class XiangqiGeneral(override val player: Player) : Piece, King {
    private val palace1 = BoxRegion(Coordinate(3, 0), Coordinate(5, 2))
    private val palace2 = BoxRegion(Coordinate(3, 7), Coordinate(5, 9))
    private val palace = CompositeRegion(listOf(palace1, palace2))

    override val moveTypes: List<Move>
        get() = listOf(
            Move.RestrictedDestination(Move.Stepper(Direction.NORTH, 1, true), palace),
            Move.RestrictedDestination(Move.Stepper(Direction.EAST, 1, true), palace),
            Move.RestrictedDestination(Move.Stepper(Direction.SOUTH, 1, true), palace),
            Move.RestrictedDestination(Move.Stepper(Direction.WEST, 1, true), palace),
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
        return "K"
    }
}
