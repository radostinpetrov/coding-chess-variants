package pieces.janggi

import moveGenerators.Direction
import moveGenerators.MoveGenerator2D
import pieces.Piece2D
import players.Player

data class Horse(override val player: Player) : Piece2D {
    override val moveGenerators: List<MoveGenerator2D>
        get() = listOf(
            MoveGenerator2D.Composite(listOf(MoveGenerator2D.Stepper(Direction.NORTH, 1, false), MoveGenerator2D.Stepper(Direction.NORTH_EAST, 1, true))),
            MoveGenerator2D.Composite(listOf(MoveGenerator2D.Stepper(Direction.NORTH, 1, false), MoveGenerator2D.Stepper(Direction.NORTH_WEST, 1, true))),
            MoveGenerator2D.Composite(listOf(MoveGenerator2D.Stepper(Direction.EAST, 1, false), MoveGenerator2D.Stepper(Direction.NORTH_EAST, 1, true))),
            MoveGenerator2D.Composite(listOf(MoveGenerator2D.Stepper(Direction.EAST, 1, false), MoveGenerator2D.Stepper(Direction.SOUTH_EAST, 1, true))),
            MoveGenerator2D.Composite(listOf(MoveGenerator2D.Stepper(Direction.SOUTH, 1, false), MoveGenerator2D.Stepper(Direction.SOUTH_EAST, 1, true))),
            MoveGenerator2D.Composite(listOf(MoveGenerator2D.Stepper(Direction.SOUTH, 1, false), MoveGenerator2D.Stepper(Direction.SOUTH_WEST, 1, true))),
            MoveGenerator2D.Composite(listOf(MoveGenerator2D.Stepper(Direction.WEST, 1, false), MoveGenerator2D.Stepper(Direction.NORTH_WEST, 1, true))),
            MoveGenerator2D.Composite(listOf(MoveGenerator2D.Stepper(Direction.WEST, 1, false), MoveGenerator2D.Stepper(Direction.SOUTH_WEST, 1, true))),
        )

    override fun getSymbol(): String {
        return "N"
    }
}
