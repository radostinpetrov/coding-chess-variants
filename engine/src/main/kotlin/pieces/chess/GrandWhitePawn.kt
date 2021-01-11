package pieces.chess

import coordinates.Coordinate2D
import moveGenerators.Direction
import moveGenerators.MoveGenerator2D
import regions.CompositeRegion
import regions.RowRegion
import players.Player
import regions.BoxRegion

/**
 * Represents a white pawn in grand chess
 */
class GrandWhitePawn(override val player: Player) : WhitePawn(player, 2, RowRegion(9), listOf(Queen(player), Bishop(player), Knight(player), Rook(player), Marshal(player), Cardinal(player))) {
    private val forcedPromotionRegion = RowRegion(9)
    private val optionalPromotionRegion = CompositeRegion(listOf(RowRegion(8), RowRegion(7)))
    private val moveList = listOf(
        MoveGenerator2D.Stepper(Direction.NORTH, 1),
        MoveGenerator2D.CaptureOnly(MoveGenerator2D.Stepper(Direction.NORTH_WEST, 1, true)),
        MoveGenerator2D.CaptureOnly(MoveGenerator2D.Stepper(Direction.NORTH_EAST, 1, true))
    )
    override val moveGenerators = listOf(
        MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.NORTH, 2), RowRegion(2)),
        MoveGenerator2D.RestrictedDestination(MoveGenerator2D.AddPromotion(moveList, forcedPromotionRegion, pawnPromotions, true), RowRegion(9)),
        MoveGenerator2D.RestrictedDestination(
            MoveGenerator2D.AddPromotion(moveList, optionalPromotionRegion, pawnPromotions, false),
            BoxRegion(Coordinate2D(0, 0), Coordinate2D(9, 8)),
        )
    )
}
