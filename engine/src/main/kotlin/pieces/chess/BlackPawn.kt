package pieces.chess

import moves.Direction
import moves.Move2D
import moves.region.RowRegion
import pieces.Piece2D
import players.Player

open class BlackPawn(override val player: Player, val startingRow: Int, val promotionRow: Int, val pawnPromotions: List<Piece2D>) : Piece2D {
    override val moveTypes: List<Move2D>
        get() = listOf(
            Move2D.Restricted(Move2D.Stepper(Direction.SOUTH, 2), RowRegion(startingRow)),
            Move2D.AddPromotion(
                Move2D.Stepper(Direction.SOUTH, 1),
                RowRegion(promotionRow),
                pawnPromotions,
                true
            ),
            Move2D.AddPromotion(
                Move2D.CaptureOnly(Move2D.Stepper(Direction.SOUTH_EAST, 1, true)),
                RowRegion(promotionRow),
                pawnPromotions,
                true
            ),
            Move2D.AddPromotion(
                Move2D.CaptureOnly(Move2D.Stepper(Direction.SOUTH_WEST, 1, true)),
                RowRegion(promotionRow),
                pawnPromotions,
                true
            )
        )

    override fun getSymbol(): String {
        return "P"
    }
}
