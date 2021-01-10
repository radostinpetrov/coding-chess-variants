package pieces.hex

import moveGenerators.DirectionHex
import players.Player
import regions.CompositeRegion
import regions.CoordinateRegion

data class WhitePawn(override val player: Player) :
    ChessPawnHex(player, DirectionHex.UP, startingRegion, promotionRegion) {
    companion object {
        val startingRegion = CompositeRegion(listOf(
            CoordinateRegion(1,4),
            CoordinateRegion(2,5),
            CoordinateRegion(3,6),
            CoordinateRegion(4,7),
            CoordinateRegion(5,8),
            CoordinateRegion(6,7),
            CoordinateRegion(7,6),
            CoordinateRegion(8,5),
            CoordinateRegion(9,4)
        ))
        val promotionRegion = CompositeRegion(listOf(
            CoordinateRegion(0,15),
            CoordinateRegion(1,16),
            CoordinateRegion(2,17),
            CoordinateRegion(3,18),
            CoordinateRegion(4,19),
            CoordinateRegion(5,20),
            CoordinateRegion(6,19),
            CoordinateRegion(7,18),
            CoordinateRegion(8,17),
            CoordinateRegion(9,16),
            CoordinateRegion(10,15)
        ))
    }

}