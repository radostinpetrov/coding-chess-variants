package main.kotlin.pieces.xiangqi

import main.kotlin.moves.Move
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

data class XiangqiCannon(override val player: Player) : Piece {

    override val moveTypes: List<Move>
        get() = listOf(
            Move.CaptureOnly(Move.Hopper(HV = true, canJumpOverSamePiece = true)),
            Move.NoCapture(Move.Slider(H = true, V = true, A = false, D = false))
        )

    override fun getSymbol(): String {
        return "C"
    }
}
