package pieces.xiangqi

import moves.Move2D
import pieces.Piece
import pieces.Piece2D
import players.Player

data class XiangqiChariot(override val player: Player) : Piece2D {

    override val moveTypes: List<Move2D>
        get() = listOf(
            Move2D.Slider(H = true, V = true, A = false, D = false)
        )

    override fun getSymbol(): String {
        return "R"
    }
}
