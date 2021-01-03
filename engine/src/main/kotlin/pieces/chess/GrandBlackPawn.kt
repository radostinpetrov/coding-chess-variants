package pieces.chess

import moves.Direction
import moves.Move2D
import moves.region.CompositeRegion
import moves.region.RowRegion
import pieces.Piece2D
import players.Player

data class GrandBlackPawn(override val player: Player) : Piece2D {
    private val pawnPromotions = listOf(Queen(player), Bishop(player), Knight(player), Rook(player), Marshal(player), Cardinal(player))
    private val forcedPromotionRegion = RowRegion(0)
    private val optionalPromotionRegion = CompositeRegion(listOf(RowRegion(1), RowRegion(2)))
    private val moveList = listOf(
        Move2D.Stepper(Direction.SOUTH, 1),
        Move2D.CaptureOnly(Move2D.Stepper(Direction.SOUTH_EAST, 1, true)),
        Move2D.CaptureOnly(Move2D.Stepper(Direction.SOUTH_WEST, 1, true))
    )
    override val moveTypes: List<Move2D> = listOf(
        Move2D.Restricted(Move2D.Stepper(Direction.SOUTH, 2), RowRegion(7)),
        Move2D.AddPromotion(moveList, forcedPromotionRegion, pawnPromotions, true),
        Move2D.AddPromotion(moveList, optionalPromotionRegion, pawnPromotions, false),
    )

    override fun getSymbol(): String {
        return "P"
    }
}
