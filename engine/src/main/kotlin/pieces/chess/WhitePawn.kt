package pieces.chess

import moves.Direction
import moves.Move2D
import moves.region.RowRegion
import pieces.Piece2D
import players.Player

open class WhitePawn(override val player: Player, val startingRow: Int, val promotionRow: Int, val pawnPromotions: List<Piece2D>) : Piece2D {
    override val moveTypes: List<Move2D> = listOf(
        Move2D.Restricted(Move2D.Stepper(Direction.NORTH, 2), RowRegion(startingRow)),
        Move2D.AddPromotion(
            listOf(
                Move2D.Stepper(Direction.NORTH, 1),
                Move2D.CaptureOnly(Move2D.Stepper(Direction.NORTH_EAST, 1, true)),
                Move2D.CaptureOnly(Move2D.Stepper(Direction.NORTH_WEST, 1, true)),
            ),
            RowRegion(promotionRow),
            pawnPromotions,
            true
        ),
    )

    override fun getSymbol(): String {
        return "P"
    }
}
