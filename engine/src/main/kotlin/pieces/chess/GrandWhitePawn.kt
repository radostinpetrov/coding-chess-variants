package pieces.chess

import moves.Direction
import moves.Move2D
import moves.region.CompositeRegion
import moves.region.RowRegion
import pieces.Piece2D
import players.Player

data class GrandWhitePawn(override val player: Player) : WhitePawn(player, 2, RowRegion(9), listOf(Queen(player), Bishop(player), Knight(player), Rook(player), Marshal(player), Cardinal(player))) {
    private val forcedPromotionRegion = RowRegion(9)
    private val optionalPromotionRegion = CompositeRegion(listOf(RowRegion(8), RowRegion(7)))
    private val moveList = listOf(
        Move2D.Stepper(Direction.NORTH, 1),
        Move2D.CaptureOnly(Move2D.Stepper(Direction.NORTH_WEST, 1, true)),
        Move2D.CaptureOnly(Move2D.Stepper(Direction.NORTH_EAST, 1, true))
    )
    override val moveTypes: List<Move2D> = listOf(
        Move2D.Restricted(Move2D.Stepper(Direction.NORTH, 2), RowRegion(2)),
        Move2D.AddPromotion(moveList, forcedPromotionRegion, pawnPromotions, true),
        Move2D.AddPromotion(moveList, optionalPromotionRegion, pawnPromotions, false),
    )

    override fun getSymbol(): String {
        return "P"
    }
}
