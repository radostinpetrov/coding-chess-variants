package main.kotlin.pieces.janggi

import main.kotlin.moves.Move
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

data class Cannon(override val player: Player) : Piece {
    private val palaceX = listOf(3, 5)
    private val palaceY = listOf(0, 2, 7, 9)

    override val moveTypes: List<Move>
        get() = listOf(
            Move.Hopper(HV = true, canJumpOverSamePiece = false),
            Move.Restricted(Move.Hopper(D = true, canJumpOverSamePiece = false), palaceX, palaceY)
        )

    override fun getSymbol(): String {
        return "C"
    }
}
