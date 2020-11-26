package pieces.xiangqi

import moves.Move
import pieces.Piece
import players.Player

class XiangqiCannon(override val player: Player) : Piece {

    override val moveTypes: List<Move>
        get() = listOf(
            Move.CaptureOnly(Move.Hopper(HV = true, canJumpOverSamePiece = true)),
            Move.NoCapture(Move.Slider(H = true, V = true, A = false, D = false))
        )

    override fun getSymbol(): String {
        return "C"
    }
}
