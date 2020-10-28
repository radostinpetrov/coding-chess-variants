package main.kotlin.pieces

import main.kotlin.moves.Move
import main.kotlin.players.Player

class Rook(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(Move.Slider(H = true, V = true, A = false, D = false))

    override fun getSymbol(): String {
        return "R"
    }
}
