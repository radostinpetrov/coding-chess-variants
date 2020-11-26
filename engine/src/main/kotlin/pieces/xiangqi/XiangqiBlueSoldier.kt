package pieces.xiangqi

import moves.Direction
import moves.Move
import pieces.Piece
import players.Player

data class XiangqiBlueSoldier(override val player: Player) : Piece {

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
