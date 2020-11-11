package main.kotlin.pieces.xiangqi

import main.kotlin.moves.Direction
import main.kotlin.moves.Move
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

class XiangqiElephant(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
                Move.Stepper(Direction.NORTH_EAST, 2, true),
                Move.Stepper(Direction.NORTH_WEST, 2, true),
                Move.Stepper(Direction.SOUTH_EAST, 2, true),
                Move.Stepper(Direction.SOUTH_WEST, 2, true),
        )

    override fun getSymbol(): String {
        return "B"
    }
}
