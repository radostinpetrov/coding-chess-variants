package pieces

import moves.Move
import moves.Slider
import players.Player

class Rook(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(Slider(H = true, V = true, A = false, D = false))

    override fun getSymbol(): String {
        TODO("Not yet implemented")
    }
}