package pieces.xiangqi

import moves.Move2D
import pieces.Piece
import pieces.Piece2D
import players.Player

data class XiangqiCannon(override val player: Player) : Piece2D {

    override val moveTypes: List<Move2D>
        get() = listOf(
            Move2D.CaptureOnly(Move2D.Hopper(HV = true, canJumpOverSamePiece = true)),
            Move2D.NoCapture(Move2D.Slider(H = true, V = true, A = false, D = false))
        )

    override fun getSymbol(): String {
        return "C"
    }
}
