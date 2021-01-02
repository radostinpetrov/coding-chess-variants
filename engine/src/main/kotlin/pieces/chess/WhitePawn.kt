package pieces.chess

import moves.Direction
import moves.Move2D
import moves.region.RowRegion
import pieces.Pawn
import pieces.Piece2D
import players.Player

open class WhitePawn(override val player: Player, val startingRow: Int, val promotionRegion: RowRegion, val pawnPromotions: List<Piece2D>) : Piece2D, Pawn {
    constructor(player: Player, startingRow: Int, promotionRow: Int, pawnPromotions: List<Piece2D>) :
            this(player, startingRow, RowRegion(promotionRow), pawnPromotions)

    override val moveTypes: List<Move2D> = listOf(
        Move2D.Restricted(Move2D.Stepper(Direction.NORTH, 2), RowRegion(startingRow)),
        Move2D.AddPromotion(
            Move2D.Stepper(Direction.NORTH, 1),
            promotionRegion,
            pawnPromotions,
            true
        ),
        Move2D.AddPromotion(
            Move2D.CaptureOnly(Move2D.Stepper(Direction.NORTH_EAST, 1, true)),
            promotionRegion,
            pawnPromotions,
            true
        ),
        Move2D.AddPromotion(
            Move2D.CaptureOnly(Move2D.Stepper(Direction.NORTH_WEST, 1, true)),
            promotionRegion,
            pawnPromotions,
            true
        ),
    )

    override fun getSymbol(): String {
        return "P"
    }
}
