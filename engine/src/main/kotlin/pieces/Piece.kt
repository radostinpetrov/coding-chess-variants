package pieces

import moves.Move
import players.Player

interface Piece {
    val moveTypes: List<Move>
    val player: Player
    fun getSymbol(): String
}
