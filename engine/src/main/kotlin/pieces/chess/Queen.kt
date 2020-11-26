package pieces.chess

import moves.Move
import pieces.Piece
import players.Player

data class Queen(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(Move.Slider(H = true, V = true, A = true, D = true))

    override fun getSymbol(): String {
        return "Q"
    }
}
