package pieces.xiangqi

import coordinates.Coordinate2D
import moves.Direction
import moves.Move2D
import moves.region.BoxRegion
import pieces.Pawn
import pieces.Piece2D
import players.Player

data class XiangqiBlueSoldier(override val player: Player) : Piece2D, Pawn {
    private val acrossRiver = BoxRegion(Coordinate2D(0, 0), Coordinate2D(9, 4))

    override val moveTypes: List<Move2D>
        get() = listOf(
            Move2D.Stepper(Direction.SOUTH, 1, true),
            Move2D.Restricted(Move2D.Stepper(Direction.EAST, 1, true), acrossRiver),
            Move2D.Restricted(Move2D.Stepper(Direction.WEST, 1, true), acrossRiver),
        )

    override fun getSymbol(): String {
        return "P"
    }
}
