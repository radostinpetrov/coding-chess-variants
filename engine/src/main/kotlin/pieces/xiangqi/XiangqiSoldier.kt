package pieces.xiangqi

import moveGenerators.Direction
import moveGenerators.MoveGenerator2D
import pieces.Pawn
import pieces.Piece2D
import players.Player
import regions.Region2D

/**
 * Represents a soldier in Xiangqi
 */
abstract class XiangqiSoldier(override val player: Player,
                              direction: Direction,
                              acrossRiver: Region2D): Piece2D, Pawn {

    override val moveGenerators =
        listOf(
            MoveGenerator2D.Stepper(direction, 1, true),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.EAST, 1, true), acrossRiver),
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.WEST, 1, true), acrossRiver),
        )

    override fun getSymbol(): String {
        return "P"
    }
}