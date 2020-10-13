package pieces

import moves.Move
import moves.Stepper

class Pawn : Piece {
    override val move: List<Move>
        get() = listOf(Stepper(Direction.NORTH, 1))

    override fun getSymbol(): String {
        TODO("Not yet implemented")
    }
}