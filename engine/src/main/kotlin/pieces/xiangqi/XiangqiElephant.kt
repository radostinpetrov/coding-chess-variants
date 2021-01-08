package pieces.xiangqi

import moveGenerators.Direction
import moveGenerators.MoveGenerator2D
import pieces.Piece2D
import players.Player
import regions.Region2D

abstract class XiangqiElephant(override val player: Player, river: Region2D) : Piece2D {

    override val moveGenerators =
        listOf(
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Stepper(Direction.NORTH_EAST, 2, true), river),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Stepper(Direction.NORTH_WEST, 2, true), river),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Stepper(Direction.SOUTH_EAST, 2, true), river),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Stepper(Direction.SOUTH_WEST, 2, true), river),
        )

    override fun getSymbol(): String {
        return "B"
    }
}