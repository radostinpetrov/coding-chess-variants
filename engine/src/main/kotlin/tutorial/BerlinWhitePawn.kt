package tutorial

import moveGenerators.Direction.*
import moveGenerators.MoveGenerator2D
import pieces.*
import pieces.chess.*
import players.Player
import regions.RowRegion

data class BerlinWhitePawn(override val player: Player) : Piece2D, Pawn {
    override val moveGenerators = listOf(
        MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(listOf(NORTH_EAST, NORTH_WEST), 2), RowRegion(1)),
        MoveGenerator2D.AddPromotion(
            listOf(
                MoveGenerator2D.Stepper(listOf(NORTH_EAST, NORTH_WEST), 1),
                MoveGenerator2D.CaptureOnly(MoveGenerator2D.Stepper(NORTH, 1, true)),
            ),
            region = RowRegion(6),
            promoPieces = listOf(Rook(player), Alfil(player), Bishop(player), Queen(player)),
            forced = true
        )
    )

    override fun getSymbol(): String = "P"
}
