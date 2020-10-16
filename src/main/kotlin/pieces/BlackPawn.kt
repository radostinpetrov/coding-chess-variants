package pieces

import moves.Direction
import moves.Move
import players.Player

class BlackPawn(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
            Move.AddForcedPromotion(
                Move.Stepper(Direction.SOUTH, 1),
                listOf(),
                listOf(0),
                listOf(Queen(player), Bishop(player), Knight(player), Rook(player))
            ),
            Move.Restricted(Move.Stepper(Direction.SOUTH, 2), listOf(), listOf(6)),
            Move.AddForcedPromotion(
                Move.CaptureOnly(Move.Leaper(1, 1)),
                listOf(),
                listOf(0),
                listOf(Queen(player), Bishop(player), Knight(player), Rook(player))
            )
        )

    override fun getSymbol(): String {
        return "P"
    }
}
