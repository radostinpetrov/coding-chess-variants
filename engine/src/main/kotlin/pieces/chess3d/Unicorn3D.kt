package pieces.chess3d

import moveGenerators.MoveGenerator3D
import pieces.Piece3D
import players.Player

/**
 * Represents a Unicorn in 3D chess
 */

data class Unicorn3D(override val player: Player) : Piece3D {
    override val moveGenerators = listOf(MoveGenerator3D.Slider3D(D3D = true))

    override fun getSymbol(): String {
        return "U"
    }
}
