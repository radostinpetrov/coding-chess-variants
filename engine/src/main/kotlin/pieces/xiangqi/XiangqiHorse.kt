package main.kotlin.pieces.xiangqi

import main.kotlin.moves.Move
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

class XiangqiHorse(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(Move.Leaper(2, 1))

    override fun getSymbol(): String {
        return "N"
    }
}
