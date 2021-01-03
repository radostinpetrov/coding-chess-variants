package pieces.chess

import moves.Direction
import moves.Move2D
import moves.region.Region
import moves.region.RowRegion
import pieces.Pawn
import pieces.Piece2D
import players.Player

open class BlackPawn(override val player: Player, val startingRow: Int, val promotionRegion: Region, val pawnPromotions: List<Piece2D>) : Piece2D,
    Pawn {
    constructor(player: Player, startingRow: Int, promotionRow: Int, pawnPromotions: List<Piece2D>):
            this(player, startingRow, RowRegion(promotionRow), pawnPromotions)

    override val moveTypes: List<Move2D>
        get() = listOf(
            Move2D.Restricted(Move2D.Stepper(Direction.SOUTH, 2), RowRegion(startingRow)),
            Move2D.AddPromotion(
                listOf(
                    Move2D.Stepper(Direction.SOUTH, 1),
                    Move2D.CaptureOnly(Move2D.Stepper(Direction.SOUTH_EAST, 1, true)),
                    Move2D.CaptureOnly(Move2D.Stepper(Direction.SOUTH_WEST, 1, true)),
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
