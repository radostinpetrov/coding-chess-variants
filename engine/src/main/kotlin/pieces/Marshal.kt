package main.kotlin.pieces

import main.kotlin.players.Player
import main.kotlin.moves.Move

class Marshal(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(Move.Leaper(2, 1), Move.Slider(H = true, V = true, A = false, D = false))

    override fun getSymbol(): String {
        return "M"
    }
}
