package pieces

import moves.Move
import moves.Move.*
import moves.Direction
import players.Player

class BlackPawn(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
            Stepper(Direction.SOUTH, 1),
            Restricted(Stepper(Direction.SOUTH, 2), listOf(), listOf(6)),
            CaptureOnly(Leaper(1, 1))
        )

    override fun getSymbol(): String {
        return "P"
    }
}