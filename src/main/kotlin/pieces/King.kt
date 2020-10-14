package pieces

import moves.Move
import moves.Leaper
import players.Player

class King(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(Leaper(1, 0), Leaper(1, 1))

    override fun getSymbol(): String {
        TODO("Not yet implemented")
    }
}