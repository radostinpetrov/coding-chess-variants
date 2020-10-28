package pieces

import moves.Move
import moves.Move.*
import main.kotlin.moves.Direction
import main.kotlin.players.Player

class WhitePawn(override val player: Player) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
            Restricted(Stepper(Direction.NORTH, 2), listOf(), listOf(1)),
            AddForcedPromotion(
                    Stepper(Direction.NORTH, 1),
                    listOf(),
                    listOf(7),
                    listOf(Queen(player), Bishop(player), Knight(player), Rook(player))
            ),
            AddForcedPromotion(
                    CaptureOnly(Stepper(Direction.NORTH_EAST, 1, true)),
                    listOf(),
                    listOf(7),
                    listOf(Queen(player), Bishop(player), Knight(player), Rook(player))
            ),
            AddForcedPromotion(
                    CaptureOnly(Stepper(Direction.NORTH_WEST, 1, true)),
                    listOf(),
                    listOf(7),
                    listOf(Queen(player), Bishop(player), Knight(player), Rook(player))
            ),
        )

    override fun getSymbol(): String {
        return "P"
    }
}