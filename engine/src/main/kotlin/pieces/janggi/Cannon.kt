package main.kotlin.pieces.janggi

import main.kotlin.moves.Move
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

class Cannon(override val player: Player) : Piece {
    private val palaceX = listOf(3, 5)
    private val palaceY = listOf(0, 2, 7, 9)

    override val moveTypes: List<Move>
        get() = listOf(
                Move.Hopper(HV = true),
                Move.Restricted(Move.Hopper(D = true), palaceX, palaceY)
        )

    override fun getSymbol(): String {
        return "C"
    }
}