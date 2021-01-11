package pieces.hex

import moveGenerators.MoveGeneratorHex
import players.Player

/**
 * Represents a rook in hexagonal chess
 */
data class HexBishop(override val player: Player) : PieceHex {
    override val moveGenerators = listOf(
        MoveGeneratorHex.Slider(diagonal = true)
    )

    override fun getSymbol(): String {
        return "B"
    }
}
