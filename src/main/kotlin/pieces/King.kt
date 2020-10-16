package pieces

import moves.Move
import players.Player

class King(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(Move.Leaper(1, 0), Move.Leaper(1, 1))

    override fun getSymbol(): String {
        return "K"
    }
}
