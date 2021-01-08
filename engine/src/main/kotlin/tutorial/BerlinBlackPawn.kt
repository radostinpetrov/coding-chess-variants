package tutorial

import moveGenerators.Direction.*
import moveGenerators.MoveGenerator2D
import pieces.chess.*
import pieces.janggi.Elephant
import players.Player
import regions.RowRegion

data class BerlinBlackPawn(override val player: Player) : WhitePawn(player, 5, RowRegion(0), listOf(Bishop(player), Elephant(player), Rook(player))) {
    private val moveList = listOf(
        MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(SOUTH_EAST, 2), RowRegion(startingRow)),
        MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(SOUTH_WEST, 2), RowRegion(startingRow)),
        MoveGenerator2D.AddPromotion(
            listOf(
                MoveGenerator2D.Stepper(SOUTH_EAST, 1),
                MoveGenerator2D.Stepper(SOUTH_WEST, 1),
                MoveGenerator2D.CaptureOnly(
                    MoveGenerator2D.Stepper(SOUTH, 1, true)
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