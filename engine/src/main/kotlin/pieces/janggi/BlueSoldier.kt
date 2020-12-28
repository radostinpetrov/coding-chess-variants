package pieces.janggi

import moves.Direction
import moves.Move
import moves.region.CoordinateRegion
import pieces.Piece
import players.Player

data class BlueSoldier(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
            Move.Stepper(Direction.EAST, 1, true),
            Move.Stepper(Direction.WEST, 1, true),
            Move.Stepper(Direction.SOUTH, 1, true),
            Move.Restricted(Move.Stepper(Direction.NORTH_EAST, 1, true), CoordinateRegion(3, 7)),
            Move.Restricted(Move.Stepper(Direction.NORTH_EAST, 1, true), CoordinateRegion(4, 8)),
            Move.Restricted(Move.Stepper(Direction.NORTH_WEST, 1, true), CoordinateRegion(5, 7)),
            Move.Restricted(Move.Stepper(Direction.NORTH_WEST, 1, true), CoordinateRegion(4, 8)),
        )

    override fun getSymbol(): String {
        return "P"
    }
}
