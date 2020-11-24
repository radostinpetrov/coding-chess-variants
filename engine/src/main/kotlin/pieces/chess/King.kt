package pieces.chess

import moves.Move
import pieces.King
import pieces.Piece
import players.Player

data class King(override val player: Player) : Piece, King {
    override val moveTypes: List<Move>
        get() = listOf(Move.Leaper(1, 0), Move.Leaper(1, 1))

    override fun getSymbol(): String {
        return "K"
    }
}
