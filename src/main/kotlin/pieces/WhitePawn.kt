package pieces

import moves.Direction
import moves.Move
import players.Player

class WhitePawn(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
            Move.AddForcedPromotion(
                Move.Stepper(Direction.NORTH, 1),
                listOf(),
                listOf(7),
                listOf(Queen(player), Bishop(player), Knight(player), Rook(player))
            ),
            Move.Restricted(Move.Stepper(Direction.NORTH, 2), listOf(), listOf(1)),
            Move.AddForcedPromotion(
                Move.CaptureOnly(Move.Leaper(1, 1)),
                listOf(),
                listOf(7),
                listOf(Queen(player), Bishop(player), Knight(player), Rook(player))
            )
        )

    override fun getSymbol(): String {
        return "P"
    }
}
