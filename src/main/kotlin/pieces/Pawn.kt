package pieces

import moves.Move
import moves.Move.Stepper
import players.Player

class Pawn(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(Stepper(Direction.NORTH, 1))

    override fun getSymbol(): String {
        return "P"
    }
}