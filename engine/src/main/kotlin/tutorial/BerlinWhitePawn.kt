package tutorial

import moveGenerators.Direction.*
import moveGenerators.MoveGenerator2D
import pieces.chess.*
import pieces.janggi.Elephant
import players.Player
import regions.RowRegion

data class BerlinWhitePawn(override val player: Player) : WhitePawn(player, 2, RowRegion(6), listOf(Bishop(player), Elephant(player), Rook(player))) {
    private val moveList = listOf(
        MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(NORTH_EAST, 2), RowRegion(startingRow)),
        MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(NORTH_WEST, 2), RowRegion(startingRow)),
        MoveGenerator2D.AddPromotion(
            listOf(
                MoveGenerator2D.Stepper(NORTH_EAST, 1),
                MoveGenerator2D.Stepper(NORTH_WEST, 1),
                MoveGenerator2D.CaptureOnly(
                    MoveGenerator2D.Stepper(NORTH, 1, true)
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