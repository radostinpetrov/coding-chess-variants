package pieces

import moves.Move
import moves.Move.*
import players.Player

class Queen(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(Slider(H = true, V = true, A = true, D = true))

    override fun getSymbol(): String {
        TODO("Not yet implemented")
    }
}