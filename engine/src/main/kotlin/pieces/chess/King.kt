package pieces.chess

import moves.Move2D
import pieces.Piece2D
import pieces.Royal
import players.Player

data class King(override val player: Player) : Piece2D, Royal {
    override val moveTypes: List<Move2D>
        get() = listOf(Move2D.Leaper(1, 0), Move2D.Leaper(1, 1))

    override fun getSymbol(): String {
        return "K"
    }
}
