package pieces.hex

import moveGenerators.DirectionHex
import players.Player
import regions.CompositeRegion
import regions.CoordinateRegion

/**
 * Represents a black pawn in hexagonal chess
 */
data class HexBlackPawn(override val player: Player) :
    ChessPawnHex(player, DirectionHex.DOWN, startingRegion, promotionRegion) {
    companion object {
        val startingRegion = CompositeRegion(listOf(
            CoordinateRegion(1,16),
            CoordinateRegion(2,15),
            CoordinateRegion(3,14),
            CoordinateRegion(4,13),
            CoordinateRegion(5,12),
            CoordinateRegion(6,13),
            CoordinateRegion(7,14),
            CoordinateRegion(8,15),
            CoordinateRegion(9,16)
        ))
        val promotionRegion = CompositeRegion(listOf(
            CoordinateRegion(0,5),
            CoordinateRegion(1,4),
            CoordinateRegion(2,3),
            CoordinateRegion(3,2),
            CoordinateRegion(4,1),
            CoordinateRegion(5,0),
            CoordinateRegion(6,1),
            CoordinateRegion(7,2),
            CoordinateRegion(8,3),
            CoordinateRegion(9,4),
            CoordinateRegion(10,5)
        ))
    }

}