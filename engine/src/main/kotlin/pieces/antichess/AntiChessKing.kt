package pieces.antichess

import moves.Move2D
import pieces.Piece2D
import players.Player

data class AntiChessKing(override val player: Player) : Piece2D {
    override val moveTypes: List<Move2D>
        get() = listOf(Move2D.Leaper(1, 0), Move2D.Leaper(1, 1))

    override fun getSymbol(): String {
        return "K"
    }
}
