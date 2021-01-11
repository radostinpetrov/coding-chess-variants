package pieces.chess3d

import moveGenerators.MoveGenerator3D
import pieces.Piece3D
import players.Player

/**
 * Represents a Bishop in 3D chess
 */
data class Bishop3D(override val player: Player) : Piece3D {
    override val moveGenerators = listOf(MoveGenerator3D.Slider3D(D2D = true, AD2D = true, D = true))

    override fun getSymbol(): String {
        return "B"
    }
}
