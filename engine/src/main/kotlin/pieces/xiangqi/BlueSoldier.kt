package main.kotlin.pieces.xiangqi

import main.kotlin.moves.Direction
import main.kotlin.moves.Move
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

class BlueSoldier(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
                Move.Stepper(Direction.EAST, 1, true),
                Move.Stepper(Direction.WEST, 1, true),
                Move.Stepper(Direction.NORTH, 1, true)
        )

    override fun getSymbol(): String {
        return "P"
    }
}