package main.kotlin.pieces

import main.kotlin.moves.Move
import main.kotlin.moves.Move.Slider
import main.kotlin.players.Player

class Bishop(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(Slider(A = true, D = true))

    override fun getSymbol(): String {
        return "B"
    }
}
