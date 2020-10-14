package pieces

import moves.Move
import moves.Slider
import players.Player

class Bishop(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(Slider(H = false, V = false, A = true, D = true))

    override fun getSymbol(): String {
        TODO("Not yet implemented")
    }
}