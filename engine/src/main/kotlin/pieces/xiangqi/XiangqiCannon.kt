package main.kotlin.pieces.janggi

import main.kotlin.moves.Move
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

class XiangqiCannon(override val player: Player) : Piece {
    private val palaceX = listOf(3, 5)
    private val palaceY = listOf(0, 2, 7, 9)

    override val moveTypes: List<Move>
        get() = listOf(
                Move.Hopper(HV = true),
        )

    override fun getSymbol(): String {
        return "C"
    }
}