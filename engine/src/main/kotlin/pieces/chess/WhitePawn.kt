package pieces.chess

import moves.Direction
import moves.Move
import moves.region.RowRegion
import pieces.Piece
import players.Player

open class WhitePawn(override val player: Player, val startingRow: Int, val promotionRow: Int, val pawnPromotions: List<Piece>) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
            Move.Restricted(Move.Stepper(Direction.NORTH, 2), RowRegion(startingRow)),
            Move.AddPromotion(
                Move.Stepper(Direction.NORTH, 1),
                RowRegion(promotionRow),
                pawnPromotions,
                true
            ),
            Move.AddForcedPromotion(
                Move.CaptureOnly(Move.Stepper(Direction.NORTH_EAST, 1, true)),
                RowRegion(promotionRow),
                pawnPromotions,
                true
            ),
            Move.AddForcedPromotion(
                Move.CaptureOnly(Move.Stepper(Direction.NORTH_WEST, 1, true)),
                RowRegion(promotionRow),
                pawnPromotions,
                true
            ),
        )

    override fun getSymbol(): String {
        return "P"
    }
}
