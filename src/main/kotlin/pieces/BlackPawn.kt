package pieces

import moves.Move
import moves.Move.*
import moves.Direction
import players.Player

class BlackPawn(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
            Stepper(Direction.SOUTH, 1),
            Restricted(Stepper(Direction.SOUTH, 2), listOf(), listOf(6)),
            CaptureOnly(Stepper(Direction.SOUTH_EAST, 1, true)),
            CaptureOnly(Stepper(Direction.SOUTH_WEST, 1, true)),
            AddForcedPromotion(
                    Stepper(Direction.SOUTH, 1),
                    listOf(),
                    listOf(0),
                    listOf(Queen(player), Bishop(player), Knight(player), Rook(player))
            ),
            AddForcedPromotion(
                    CaptureOnly(Stepper(Direction.SOUTH_EAST, 1, true)),
                    listOf(),
                    listOf(0),
                    listOf(Queen(player), Bishop(player), Knight(player), Rook(player))
            ),
            AddForcedPromotion(
                    CaptureOnly(Stepper(Direction.SOUTH_WEST, 1, true)),
                    listOf(),
                    listOf(0),
                    listOf(Queen(player), Bishop(player), Knight(player), Rook(player))
            )
        )

    override fun getSymbol(): String {
        return "P"
    }
}