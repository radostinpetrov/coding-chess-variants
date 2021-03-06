package pieces.xiangqi

import moveGenerators.MoveGenerator2D
import pieces.Piece2D
import players.Player

/**
 * Represents a chariot in Xiangqi
 */
data class XiangqiChariot(override val player: Player) : Piece2D {

    override val moveGenerators =
        listOf(
            MoveGenerator2D.Slider(H = true, V = true, A = false, D = false)
        )

    override fun getSymbol(): String {
        return "R"
    }
}
