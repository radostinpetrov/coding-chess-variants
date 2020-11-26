package pieces.chess

import moves.Move
import pieces.Piece
import players.Player

data class Knight(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(Move.Leaper(2, 1))

    override fun getSymbol(): String {
        return "N"
    }
}
