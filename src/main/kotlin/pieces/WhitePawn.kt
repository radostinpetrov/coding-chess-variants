package pieces

import moves.Move
import moves.Move.*
import moves.Direction
import players.Player

class WhitePawn(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
            AddForcedPromotion(Stepper(Direction.NORTH, 1), listOf(), listOf(7), listOf(Queen(player), Bishop(player), Knight(player), Rook(player))),
            Restricted(Stepper(Direction.NORTH, 2), listOf(), listOf(1)),
            AddForcedPromotion(Move.CaptureOnly(Move.Leaper(1, 1)),  listOf(), listOf(7), listOf(Queen(player), Bishop(player), Knight(player), Rook(player)))
        )

    override fun getSymbol(): String {
        return "P"
    }
}