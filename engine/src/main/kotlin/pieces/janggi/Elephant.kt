package main.kotlin.pieces.janggi

import main.kotlin.moves.Direction
import main.kotlin.moves.Move
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

class Elephant(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
            Move.Composite(listOf(Move.Stepper(Direction.NORTH, 1, false), Move.Stepper(Direction.NORTH_EAST, 2, true))),
            Move.Composite(listOf(Move.Stepper(Direction.NORTH, 1, false), Move.Stepper(Direction.NORTH_WEST, 2, true))),
            Move.Composite(listOf(Move.Stepper(Direction.EAST, 1, false), Move.Stepper(Direction.NORTH_EAST, 2, true))),
            Move.Composite(listOf(Move.Stepper(Direction.EAST, 1, false), Move.Stepper(Direction.SOUTH_EAST, 2, true))),
            Move.Composite(listOf(Move.Stepper(Direction.SOUTH, 1, false), Move.Stepper(Direction.SOUTH_EAST, 2, true))),
            Move.Composite(listOf(Move.Stepper(Direction.SOUTH, 1, false), Move.Stepper(Direction.SOUTH_WEST, 2, true))),
            Move.Composite(listOf(Move.Stepper(Direction.WEST, 1, false), Move.Stepper(Direction.NORTH_WEST, 2, true))),
            Move.Composite(listOf(Move.Stepper(Direction.WEST, 1, false), Move.Stepper(Direction.SOUTH_WEST, 2, true))),
        )

    override fun getSymbol(): String {
        return "B"
    }
}
