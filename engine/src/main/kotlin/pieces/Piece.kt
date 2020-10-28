package pieces

import moves.Move
import main.kotlin.players.Player

interface Piece {
    val moveTypes: List<Move>
    val player: Player
    fun getSymbol(): String
}
