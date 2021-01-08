package pieces.xiangqi

import coordinates.Coordinate2D
import moveGenerators.Direction
import moveGenerators.MoveGenerator2D
import regions.BoxRegion
import pieces.Pawn
import pieces.Piece2D
import players.Player

data class XiangqiBlueSoldier(override val player: Player) : Piece2D, Pawn {
    private val acrossRiver = BoxRegion(Coordinate2D(0, 0), Coordinate2D(9, 4))

    override val moveGenerators =
        listOf(
            MoveGenerator2D.Stepper(Direction.SOUTH, 1, true),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.EAST, 1, true), acrossRiver),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.WEST, 1, true), acrossRiver),
        )

    override fun getSymbol(): String {
        return "P"
    }
}
