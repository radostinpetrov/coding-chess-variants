package main.kotlin.pieces.xiangqi

import main.kotlin.moves.Direction
import main.kotlin.moves.Move
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

data class XiangqiAdvisor(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
            Move.Restricted(Move.Leaper(1, 1), listOf(4), listOf(1, 8)),
            Move.Restricted(Move.Stepper(Direction.NORTH_EAST, 1, true), listOf(3), listOf(7)),
            Move.Restricted(Move.Stepper(Direction.NORTH_WEST, 1, true), listOf(5), listOf(7)),
            Move.Restricted(Move.Stepper(Direction.SOUTH_EAST, 1, true), listOf(3), listOf(9)),
            Move.Restricted(Move.Stepper(Direction.SOUTH_WEST, 1, true), listOf(5), listOf(9)),
            Move.Restricted(Move.Stepper(Direction.SOUTH_EAST, 1, true), listOf(3), listOf(2)),
            Move.Restricted(Move.Stepper(Direction.SOUTH_WEST, 1, true), listOf(5), listOf(2)),
            Move.Restricted(Move.Stepper(Direction.NORTH_EAST, 1, true), listOf(3), listOf(0)),
            Move.Restricted(Move.Stepper(Direction.NORTH_WEST, 1, true), listOf(5), listOf(0)),
        )

    override fun getSymbol(): String {
        return "A"
    }
}
