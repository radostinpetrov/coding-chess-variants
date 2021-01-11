package pieces.hex

import moveGenerators.DirectionHex.*
import moveGenerators.MoveGenerator2D
import moveGenerators.MoveGeneratorHex
import pieces.Piece2D
import pieces.Royal
import players.Player

/**
 * Represents a king in standard chess
 */
data class HexKing(override val player: Player) : PieceHex, Royal {
    override val moveGenerators = listOf(MoveGeneratorHex.Stepper(
        listOf(UP, UP_RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, UP_LEFT,UP_UP_RIGHT, DOWN_DOWN_RIGHT, RIGHT, DOWN_DOWN_LEFT,
            UP_UP_LEFT, LEFT),
        1,
        true
    ))

    override fun getSymbol(): String {
        return "K"
    }
}
