package pieces.hex

import moveGenerators.MoveGeneratorHex
import players.Player

/**
 * Represents a rook in standard chess
 */
data class HexRook(override val player: Player) : PieceHex {
    override val moveGenerators = listOf(
        MoveGeneratorHex.Slider(orthogonal = true)
    )

    override fun getSymbol(): String {
        return "R"
    }
}
