package main.kotlin.pieces.xiangqi

import main.kotlin.moves.Direction
import main.kotlin.moves.Move
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

data class XiangqiBlueElephant(override val player: Player) : Piece {
    private val river = listOf(5, 6, 7, 8, 9)

    override val moveTypes: List<Move>
        get() = listOf(
            Move.RestrictedDestination(Move.Stepper(Direction.NORTH_EAST, 2, true), listOf(), river),
            Move.RestrictedDestination(Move.Stepper(Direction.NORTH_WEST, 2, true), listOf(), river),
            Move.RestrictedDestination(Move.Stepper(Direction.SOUTH_EAST, 2, true), listOf(), river),
            Move.RestrictedDestination(Move.Stepper(Direction.SOUTH_WEST, 2, true), listOf(), river),
        )

    override fun getSymbol(): String {
        return "B"
    }
}
