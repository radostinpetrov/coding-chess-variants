package pieces.chess

import moveGenerators.Direction
import moveGenerators.MoveGenerator2D
import regions.RowRegion
import pieces.Pawn
import pieces.Piece2D
import players.Player

open class WhitePawn(override val player: Player, val startingRow: Int, val promotionRegion: RowRegion, val pawnPromotions: List<Piece2D>) : Piece2D, Pawn {
    constructor(player: Player, startingRow: Int, promotionRow: Int, pawnPromotions: List<Piece2D>) :
        this(player, startingRow, RowRegion(promotionRow), pawnPromotions)

    override val moveGenerators =
        listOf(
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.NORTH, 2), RowRegion(startingRow)),
            MoveGenerator2D.AddPromotion(
                listOf(
                    MoveGenerator2D.Stepper(Direction.NORTH, 1),
                    MoveGenerator2D.CaptureOnly(MoveGenerator2D.Stepper(Direction.NORTH_EAST, 1, true)),
                    MoveGenerator2D.CaptureOnly(MoveGenerator2D.Stepper(Direction.NORTH_WEST, 1, true)),
                ),
                promotionRegion,
                pawnPromotions,
                true
            ),
        )

    override fun equals(other: Any?): Boolean {
        return (other is WhitePawn && other.player == this.player && other.startingRow == this.startingRow && other.promotionRegion == this.promotionRegion && other.pawnPromotions == this.pawnPromotions)
    }

    override fun getSymbol(): String {
        return "P"
    }

    override fun hashCode(): Int {
        var result = player.hashCode()
        result = 31 * result + startingRow
        result = 31 * result + promotionRegion.hashCode()
        result = 31 * result + pawnPromotions.hashCode()
        result = 31 * result + moveGenerators.hashCode()
        return result
    }
}
