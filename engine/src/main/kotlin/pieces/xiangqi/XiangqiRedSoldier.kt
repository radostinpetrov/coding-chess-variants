package pieces.xiangqi

import Coordinate
import moves.Direction
import moves.Move
import moves.region.BoxRegion
import pieces.Piece
import players.Player

data class XiangqiRedSoldier(override val player: Player) : Piece {
    private val acrossRiver = BoxRegion(Coordinate(0, 5), Coordinate(9, 9))

    override val moveTypes: List<Move>
        get() = listOf(
            Move.Stepper(Direction.NORTH, 1, true),
            Move.Restricted(Move.Stepper(Direction.EAST, 1, true), acrossRiver),
            Move.Restricted(Move.Stepper(Direction.WEST, 1, true), acrossRiver),
        )

    override fun getSymbol(): String {
        return "P"
    }
}
