package main.kotlin.pieces.xiangqi

import main.kotlin.moves.Direction
import main.kotlin.moves.Move
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

class XiangqiBlueSoldier(override val player: Player) : Piece {

    private val acrossRiverY = listOf(0, 1, 2, 3, 4)

    override val moveTypes: List<Move>
        get() = listOf(
                Move.Stepper(Direction.SOUTH, 1, true),
                Move.Restricted(Move.Stepper(Direction.EAST, 1, true), listOf(), acrossRiverY),
                Move.Restricted(Move.Stepper(Direction.WEST, 1, true), listOf(), acrossRiverY),
        )

    override fun getSymbol(): String {
        return "P"
    }
}