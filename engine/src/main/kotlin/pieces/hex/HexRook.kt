package pieces.hex

import moveGenerators.DirectionHex.*
import moveGenerators.MoveGenerator2D
import moveGenerators.MoveGeneratorHex
import pieces.Piece2D
import players.Player

/**
 * Represents a rook in standard chess
 */
data class HexRook(override val player: Player) : PieceHex {
    override val moveGenerators = listOf(
        MoveGeneratorHex.Slider(listOf(UP, UP_RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, UP_LEFT))
    )

    override fun getSymbol(): String {
        return "R"
    }
}
