package pieces.chess

import moveGenerators.Direction
import regions.RowRegion
import pieces.Piece2D
import players.Player

/**
 * Represents a white pawn
 */
open class WhitePawn(override val player: Player, val startingRow: Int, val promotionRegion: RowRegion, val pawnPromotions: List<Piece2D>)
    : Piece2D, ChessPawn(player, Direction.NORTH, startingRow, promotionRegion, pawnPromotions){
    constructor(player: Player, startingRow: Int, promotionRow: Int, pawnPromotions: List<Piece2D>) :
        this(player, startingRow, RowRegion(promotionRow), pawnPromotions)

    override fun equals(other: Any?): Boolean {
        return (other is WhitePawn && other.player == this.player && other.startingRow == this.startingRow && other.promotionRegion == this.promotionRegion && other.pawnPromotions == this.pawnPromotions)
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
