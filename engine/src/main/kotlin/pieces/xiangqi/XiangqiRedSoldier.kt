package main.kotlin.pieces.janggi

import main.kotlin.moves.Direction
import main.kotlin.moves.Move
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

class XiangqiRedSoldier(override val player: Player) : Piece {

    private val acrossRiverX = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
    private val acrossRiverY = listOf(0, 1, 2, 3, 4)

    override val moveTypes: List<Move>
        get() = listOf(
                Move.Stepper(Direction.NORTH, 1, true),
                Move.Restricted(Move.Stepper(Direction.EAST, 1, true), acrossRiverX, acrossRiverY),
                Move.Restricted(Move.Stepper(Direction.WEST, 1, true), acrossRiverX, acrossRiverY),
        )

    override fun getSymbol(): String {
        return "P"
    }
}