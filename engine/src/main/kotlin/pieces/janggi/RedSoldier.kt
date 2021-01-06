package pieces.janggi

import moveGenerators.Direction
import moveGenerators.MoveGenerator2D
import regions.CoordinateRegion
import pieces.Pawn
import pieces.Piece2D
import players.Player

data class RedSoldier(override val player: Player) : Piece2D, Pawn {
    override val moveGenerators: List<MoveGenerator2D>
        get() = listOf(
            MoveGenerator2D.Stepper(Direction.EAST, 1, true),
            MoveGenerator2D.Stepper(Direction.WEST, 1, true),
            MoveGenerator2D.Stepper(Direction.NORTH, 1, true),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.NORTH_EAST, 1, true), CoordinateRegion(3, 7)),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.NORTH_EAST, 1, true), CoordinateRegion(4, 8)),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.NORTH_WEST, 1, true), CoordinateRegion(5, 7)),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.NORTH_WEST, 1, true), CoordinateRegion(4, 8)),
        )

    override fun getSymbol(): String {
        return "P"
    }
}
