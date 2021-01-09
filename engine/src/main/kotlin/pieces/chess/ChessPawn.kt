package pieces.chess

import moveGenerators.Direction
import moveGenerators.MoveGenerator2D
import pieces.Pawn
import pieces.Piece2D
import players.Player
import regions.Region2D
import regions.RowRegion

/**
 * Represents a pawn in standard chess
 *
 * @param player the player who owns the pawn
 * @param direction the direction that the pawn is facing
 *        (determines the direction that pawn moves to)
 * @param startingRow the row that the pawn starts from
 *        (used for double step move)
 * @param promotionRegion the region that the promotion can occur
 * @param pawnPromotions the pieces that the pawn can be promoted to
 */
abstract class ChessPawn(override val player: Player,
                         private val direction: Direction,
                         private val startingRow: Int,
                         private val promotionRegion: Region2D,
                         private val pawnPromotions: List<Piece2D>)
    : Pawn, Piece2D {

    private val captureOnlyDir = if (direction == Direction.SOUTH)
            listOf(Direction.SOUTH_EAST, Direction.SOUTH_WEST)
            else listOf(Direction.NORTH_EAST, Direction.NORTH_WEST)

    override val moveGenerators: List<MoveGenerator2D>
        get() = listOf(
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(direction, 2), RowRegion(startingRow)),
            MoveGenerator2D.AddPromotion(
                listOf(
                    MoveGenerator2D.Stepper(direction, 1),
                    MoveGenerator2D.CaptureOnly(
                        MoveGenerator2D.Stepper(captureOnlyDir, 1, true)
                    ),
                ),
                promotionRegion,
                pawnPromotions,
                true
            )
        )

    override fun getSymbol(): String {
        return "P"
    }
}