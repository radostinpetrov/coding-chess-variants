package pieces.xiangqi

import moves.Direction
import moves.Move2D
import pieces.Piece
import pieces.Piece2D
import players.Player

data class XiangqiHorse(override val player: Player) : Piece2D {
    override val moveTypes: List<Move2D>
        get() = listOf(
            Move2D.Composite(listOf(Move2D.Stepper(Direction.NORTH, 1, false), Move2D.Stepper(Direction.NORTH_EAST, 1, true))),
            Move2D.Composite(listOf(Move2D.Stepper(Direction.NORTH, 1, false), Move2D.Stepper(Direction.NORTH_WEST, 1, true))),
            Move2D.Composite(listOf(Move2D.Stepper(Direction.EAST, 1, false), Move2D.Stepper(Direction.NORTH_EAST, 1, true))),
            Move2D.Composite(listOf(Move2D.Stepper(Direction.EAST, 1, false), Move2D.Stepper(Direction.SOUTH_EAST, 1, true))),
            Move2D.Composite(listOf(Move2D.Stepper(Direction.SOUTH, 1, false), Move2D.Stepper(Direction.SOUTH_EAST, 1, true))),
            Move2D.Composite(listOf(Move2D.Stepper(Direction.SOUTH, 1, false), Move2D.Stepper(Direction.SOUTH_WEST, 1, true))),
            Move2D.Composite(listOf(Move2D.Stepper(Direction.WEST, 1, false), Move2D.Stepper(Direction.NORTH_WEST, 1, true))),
            Move2D.Composite(listOf(Move2D.Stepper(Direction.WEST, 1, false), Move2D.Stepper(Direction.SOUTH_WEST, 1, true))),
        )

    override fun getSymbol(): String {
        return "N"
    }
}
