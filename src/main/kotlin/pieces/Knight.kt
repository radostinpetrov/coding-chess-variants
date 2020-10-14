package pieces

import moves.Move
import moves.Leaper
import players.Player

class Knight(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(Leaper(2, 1))

    override fun getSymbol(): String {
        TODO("Not yet implemented")
    }
}