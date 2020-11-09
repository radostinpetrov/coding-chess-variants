package main.kotlin.pieces.xiangqi

import main.kotlin.moves.Move
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

class Cannon(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
                Move.Hopper
        )

    override fun getSymbol(): String {
        return "C"
    }
}