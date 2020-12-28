package pieces.chess

import moves.Direction
import moves.Move
import moves.region.CompositeRegion
import moves.region.RowRegion
import pieces.Piece
import players.Player

data class GrandWhitePawn(override val player: Player) : Piece {
    private val pawnPromotions = listOf(Queen(player), Bishop(player), Knight(player), Rook(player), Marshal(player), Cardinal(player))
    private val forcedPromotionRegion = RowRegion(9)
    private val optionalPromotionRegion = CompositeRegion(listOf(RowRegion(8), RowRegion(7)))
    private val moveList = listOf(
        Move.Stepper(Direction.SOUTH, 1),
        Move.CaptureOnly(Move.Stepper(Direction.SOUTH_EAST, 1, true)),
        Move.CaptureOnly(Move.Stepper(Direction.SOUTH_WEST, 1, true))
    )
    override val moveTypes: List<Move> = listOf(
        Move.Restricted(Move.Stepper(Direction.SOUTH, 2), RowRegion(2)),
        Move.AddPromotion(moveList[0], forcedPromotionRegion, pawnPromotions, true),
        Move.AddPromotion(moveList[1], forcedPromotionRegion, pawnPromotions, true),
        Move.AddPromotion(moveList[2], forcedPromotionRegion, pawnPromotions, true),
        Move.AddPromotion(moveList[0], optionalPromotionRegion, pawnPromotions, false),
        Move.AddPromotion(moveList[1], optionalPromotionRegion, pawnPromotions, false),
        Move.AddPromotion(moveList[2], optionalPromotionRegion, pawnPromotions, false)
    )

    override fun getSymbol(): String {
        return "P"
    }
}
