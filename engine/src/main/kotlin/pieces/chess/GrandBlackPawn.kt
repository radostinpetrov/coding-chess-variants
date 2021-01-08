package pieces.chess

import moveGenerators.Direction
import moveGenerators.MoveGenerator2D
import regions.CompositeRegion
import regions.RowRegion
import players.Player

/**
 * Represents a black pawn in grand chess
 */
class GrandBlackPawn(override val player: Player) : BlackPawn(player, 2, RowRegion(0), listOf(Queen(player), Bishop(player), Knight(player), Rook(player), Marshal(player), Cardinal(player))) {
    private val forcedPromotionRegion = RowRegion(0)
    private val optionalPromotionRegion = CompositeRegion(listOf(RowRegion(1), RowRegion(2)))
    private val moveList = listOf(
        MoveGenerator2D.Stepper(Direction.SOUTH, 1),
        MoveGenerator2D.CaptureOnly(MoveGenerator2D.Stepper(Direction.SOUTH_EAST, 1, true)),
        MoveGenerator2D.CaptureOnly(MoveGenerator2D.Stepper(Direction.SOUTH_WEST, 1, true))
    )
    override val moveGenerators = listOf(
        MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.SOUTH, 2), RowRegion(7)),
        MoveGenerator2D.AddPromotion(moveList, forcedPromotionRegion, pawnPromotions, true),
        MoveGenerator2D.AddPromotion(moveList, optionalPromotionRegion, pawnPromotions, false),
    )
}
