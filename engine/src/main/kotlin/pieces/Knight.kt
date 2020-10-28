package main.kotlin.pieces

import main.kotlin.moves.Move
import main.kotlin.players.Player

class Knight(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(Move.Leaper(2, 1))

    override fun getSymbol(): String {
        return "N"
    }
}
