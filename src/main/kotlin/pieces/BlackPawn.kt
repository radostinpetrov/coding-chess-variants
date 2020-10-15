package pieces

import moves.Move
import moves.Move.Stepper
import moves.Direction
import players.Player

class BlackPawn(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(Stepper(Direction.SOUTH, 1))

    override fun getSymbol(): String {
        return "P"
    }
}