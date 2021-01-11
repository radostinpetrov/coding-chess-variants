package pieces.hex

import coordinates.Coordinate2D
import moveGenerators.MoveGenerator2D
import moveGenerators.MoveGeneratorHex
import pieces.Piece2D
import players.Player

/**
 * Represents a kinght in standard chess
 */
data class HexKnight(override val player: Player) : PieceHex {
    override val moveGenerators = listOf(MoveGeneratorHex.Leaper(listOf(
        Coordinate2D(1,5),
        Coordinate2D(-1,5),
        Coordinate2D(1,-5),
        Coordinate2D(-1,-5),
        Coordinate2D(2,4),
        Coordinate2D(-2,4),
        Coordinate2D(2,-4),
        Coordinate2D(-2,-4),
        Coordinate2D(3,1),
        Coordinate2D(-3,1),
        Coordinate2D(3,-1),
        Coordinate2D(-3,-1)
    )))

    override fun getSymbol(): String {
        return "N"
    }
}
