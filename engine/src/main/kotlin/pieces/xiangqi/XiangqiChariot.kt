package pieces.xiangqi

import moves.Move
import pieces.Piece
import players.Player

class XiangqiChariot(override val player: Player) : Piece {

    override val moveTypes: List<Move>
        get() = listOf(
            Move.Slider(H = true, V = true, A = false, D = false)
        )

    override fun getSymbol(): String {
        return "R"
    }
}
