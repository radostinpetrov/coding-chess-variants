package main.kotlin.pieces.chess

import main.kotlin.moves.Move
import main.kotlin.pieces.King
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

class King(override val player: Player) : Piece, King {
    override val moveTypes: List<Move>
        get() = listOf(Move.Leaper(1, 0), Move.Leaper(1, 1))

    override fun getSymbol(): String {
        return "K"
    }
}
