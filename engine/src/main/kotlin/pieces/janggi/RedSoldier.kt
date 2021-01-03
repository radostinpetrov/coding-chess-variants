package pieces.janggi

import moves.Direction
import moves.Move2D
import moves.region.CoordinateRegion
import pieces.Pawn
import pieces.Piece
import pieces.Piece2D
import players.Player

data class RedSoldier(override val player: Player) : Piece2D, Pawn {
    override val moveTypes: List<Move2D>
        get() = listOf(
            Move2D.Stepper(Direction.EAST, 1, true),
            Move2D.Stepper(Direction.WEST, 1, true),
            Move2D.Stepper(Direction.NORTH, 1, true),
            Move2D.Restricted(Move2D.Stepper(Direction.NORTH_EAST, 1, true), CoordinateRegion(3, 7)),
            Move2D.Restricted(Move2D.Stepper(Direction.NORTH_EAST, 1, true), CoordinateRegion(4, 8)),
            Move2D.Restricted(Move2D.Stepper(Direction.NORTH_WEST, 1, true), CoordinateRegion(5, 7)),
            Move2D.Restricted(Move2D.Stepper(Direction.NORTH_WEST, 1, true), CoordinateRegion(4, 8)),
        )

    override fun getSymbol(): String {
        return "P"
    }
}
