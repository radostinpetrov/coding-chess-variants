package pieces.janggi

import moveGenerators.Direction
import moveGenerators.MoveGenerator2D
import pieces.Pawn
import pieces.Piece2D
import players.Player
import regions.CoordinateRegion

/**
 * Represents a soldier in Janggi
 */
abstract class JanggiSoldier(override val player: Player, direction: Direction) : Piece2D, Pawn {
    private val palaceDir = if (direction == Direction.SOUTH)
        listOf(Direction.SOUTH_EAST, Direction.SOUTH_WEST)
        else listOf(Direction.NORTH_EAST, Direction.NORTH_WEST)

    override val moveGenerators =
        listOf(
            MoveGenerator2D.Stepper(Direction.EAST, 1, true),
            MoveGenerator2D.Stepper(Direction.WEST, 1, true),
            MoveGenerator2D.Stepper(direction, 1, true),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(palaceDir[0], 1, true), CoordinateRegion(3, 7)),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(palaceDir[0], 1, true), CoordinateRegion(4, 8)),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(palaceDir[1], 1, true), CoordinateRegion(5, 7)),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(palaceDir[1], 1, true), CoordinateRegion(4, 8)),
        )

    override fun getSymbol(): String {
        return "P"
    }
}