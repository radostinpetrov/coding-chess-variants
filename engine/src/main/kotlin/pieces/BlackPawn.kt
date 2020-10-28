package main.kotlin.pieces

import main.kotlin.moves.Direction
import main.kotlin.moves.Move
import main.kotlin.players.Player

class BlackPawn(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
            Move.Restricted(Move.Stepper(Direction.SOUTH, 2), listOf(), listOf(6)),
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
