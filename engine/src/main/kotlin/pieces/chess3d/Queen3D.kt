package pieces.chess3d

import moveGenerators.MoveGenerator3D
import pieces.Piece3D
import players.Player

/**
 * Represents a Queen in 3D chess
 */

data class Queen3D(override val player: Player) : Piece3D {
    override val moveGenerators = listOf(MoveGenerator3D.Slider3D(X = true, Y = true, Z = true, AD2D = true, D2D = true, D3D = true, D = true))

    override fun getSymbol(): String {
        return "Q"
    }
}
