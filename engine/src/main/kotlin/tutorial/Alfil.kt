package tutorial

import moveGenerators.MoveGenerator2D
import pieces.Piece2D
import players.Player

data class Alfil(override val player: Player) : Piece2D {
    override val moveGenerators: List<MoveGenerator2D> = listOf(
        MoveGenerator2D.Leaper(2, 2)
    )

    override fun getSymbol(): String {
        return "E"
    }
}