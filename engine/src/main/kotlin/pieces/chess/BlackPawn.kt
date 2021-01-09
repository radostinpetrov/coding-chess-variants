package pieces.chess

import moveGenerators.Direction
import regions.Region2D
import regions.RowRegion
import pieces.Piece2D
import players.Player

/**
 * Represents a black pawn
 */
open class BlackPawn(override val player: Player, val startingRow: Int, val promotionRegion: Region2D, val pawnPromotions: List<Piece2D>)
    : Piece2D, ChessPawn(player, Direction.SOUTH, startingRow, promotionRegion, pawnPromotions) {
    constructor(player: Player, startingRow: Int, promotionRow: Int, pawnPromotions: List<Piece2D>):
            this(player, startingRow, RowRegion(promotionRow), pawnPromotions)

    override fun equals(other: Any?): Boolean {
        if (other !is BlackPawn) {
            return false
        }

        return (other.startingRow == this.startingRow && other.promotionRegion == this.promotionRegion && other.pawnPromotions == this.pawnPromotions)
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
