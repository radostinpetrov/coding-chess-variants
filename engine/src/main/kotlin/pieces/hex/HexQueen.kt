package pieces.hex

import moveGenerators.MoveGeneratorHex
import players.Player

/**
 * Represents a rook in hexagonal chess
 */
data class HexQueen(override val player: Player) : PieceHex {
    override val moveGenerators = listOf(
        MoveGeneratorHex.Slider(orthogonal = true, diagonal= true)
    )

    override fun getSymbol(): String {
        return "Q"
    }
}
