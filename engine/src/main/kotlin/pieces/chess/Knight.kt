package pieces.chess

import moveGenerators.MoveGenerator2D
import pieces.Piece2D
import players.Player

data class Knight(override val player: Player) : Piece2D {
    override val moveGenerators = listOf(MoveGenerator2D.Leaper(2, 1))

    override fun getSymbol(): String {
        return "N"
    }
}
