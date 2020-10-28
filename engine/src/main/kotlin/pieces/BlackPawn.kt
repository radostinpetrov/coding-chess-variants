package pieces

import moves.Move
import moves.Move.*
import main.kotlin.moves.Direction
import main.kotlin.players.Player

class BlackPawn(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
            Restricted(Stepper(Direction.SOUTH, 2), listOf(), listOf(6)),
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