package main.kotlin.pieces.xiangqi

import main.kotlin.moves.Move
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

class XiangqiChariot(override val player: Player) : Piece {

    override val moveTypes: List<Move>
        get() = listOf(
            Move.Slider(H = true, V = true, A = false, D = false)
        )

    override fun getSymbol(): String {
        return "R"
    }
}
