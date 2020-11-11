package main.kotlin.pieces.xiangqi

import main.kotlin.moves.Direction
import main.kotlin.moves.Move
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

class XiangqiHorse(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
                Move.Composite(listOf(Move.Stepper(Direction.NORTH, 1, false), Move.Stepper(Direction.NORTH_EAST, 1, true))),
                Move.Composite(listOf(Move.Stepper(Direction.NORTH, 1, false), Move.Stepper(Direction.NORTH_WEST, 1, true))),
                Move.Composite(listOf(Move.Stepper(Direction.EAST, 1, false), Move.Stepper(Direction.NORTH_EAST, 1, true))),
                Move.Composite(listOf(Move.Stepper(Direction.EAST, 1, false), Move.Stepper(Direction.SOUTH_EAST, 1, true))),
                Move.Composite(listOf(Move.Stepper(Direction.SOUTH, 1, false), Move.Stepper(Direction.SOUTH_EAST, 1, true))),
                Move.Composite(listOf(Move.Stepper(Direction.SOUTH, 1, false), Move.Stepper(Direction.SOUTH_WEST, 1, true))),
                Move.Composite(listOf(Move.Stepper(Direction.WEST, 1, false), Move.Stepper(Direction.NORTH_WEST, 1, true))),
                Move.Composite(listOf(Move.Stepper(Direction.WEST, 1, false), Move.Stepper(Direction.SOUTH_WEST, 1, true))),
        )

    override fun getSymbol(): String {
        return "N"
    }
}
