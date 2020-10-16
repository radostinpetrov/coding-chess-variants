package pieces

import moves.Move
import moves.Move.*
import moves.Direction
import players.Player

class BlackPawn(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
            AddForcedPromotion(Stepper(Direction.SOUTH, 1), listOf(), listOf(0), listOf(Queen(player), Bishop(player), Knight(player), Rook(player))),
            Restricted(Stepper(Direction.SOUTH, 2), listOf(), listOf(6)),
            AddForcedPromotion(Move.CaptureOnly(Move.Leaper(1, 1)),  listOf(), listOf(0), listOf(Queen(player), Bishop(player), Knight(player), Rook(player))))

    override fun getSymbol(): String {
        return "P"
    }
}