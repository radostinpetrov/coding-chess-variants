package pieces

import moves.Move
import moves.Move.Stepper
import moves.Direction
import players.Player

class WhitePawn(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
            Stepper(Direction.NORTH, 1),
            Move.Restricted(Stepper(Direction.NORTH, 2), listOf(), listOf(1)),
            Move.CaptureOnly(Move.Leaper(1, 1))
        )

    override fun getSymbol(): String {
        return "P"
    }
}