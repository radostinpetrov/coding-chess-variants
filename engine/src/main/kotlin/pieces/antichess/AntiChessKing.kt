package pieces.antichess

import moves.Move
import pieces.Piece
import players.Player

data class AntiChessKing(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(Move.Leaper(1, 0), Move.Leaper(1, 1))

    override fun getSymbol(): String {
        return "K"
    }
}
