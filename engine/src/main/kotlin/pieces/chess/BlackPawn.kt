package pieces.chess

import moves.Direction
import moves.Move
import pieces.Piece
import players.Player

open class BlackPawn(override val player: Player, val startingRow: Int) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
            Move.Restricted(Move.Stepper(Direction.SOUTH, 2), listOf(), listOf(startingRow)),
            Move.AddForcedPromotion(
                Move.Stepper(Direction.SOUTH, 1),
                listOf(),
                listOf(0),
                listOf(Queen(player), Bishop(player), Knight(player), Rook(player))
            ),
            Move.AddForcedPromotion(
                Move.CaptureOnly(Move.Stepper(Direction.SOUTH_EAST, 1, true)),
                listOf(),
                listOf(0),
                listOf(Queen(player), Bishop(player), Knight(player), Rook(player))
            ),
            Move.AddForcedPromotion(
                Move.CaptureOnly(Move.Stepper(Direction.SOUTH_WEST, 1, true)),
                listOf(),
                listOf(0),
                listOf(Queen(player), Bishop(player), Knight(player), Rook(player))
            )
        )

    override fun getSymbol(): String {
        return "P"
    }
}
