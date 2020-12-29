package pieces.chess

import moves.Move2D
import pieces.Piece2D
import players.Player

data class Knight(override val player: Player) : Piece2D {
    override val moveTypes: List<Move2D>
        get() = listOf(Move2D.Leaper(2, 1))

    override fun getSymbol(): String {
        return "N"
    }
}
