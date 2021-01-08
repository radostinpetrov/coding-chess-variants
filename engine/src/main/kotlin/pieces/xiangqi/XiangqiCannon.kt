package pieces.xiangqi

import moveGenerators.MoveGenerator2D
import pieces.Piece2D
import players.Player

data class XiangqiCannon(override val player: Player) : Piece2D {

    override val moveGenerators =
        listOf(
            MoveGenerator2D.CaptureOnly(MoveGenerator2D.Hopper(HV = true, canJumpOverSamePiece = true)),
            MoveGenerator2D.NoCapture(MoveGenerator2D.Slider(H = true, V = true, A = false, D = false))
        )

    override fun getSymbol(): String {
        return "C"
    }
}
