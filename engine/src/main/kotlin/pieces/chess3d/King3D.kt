package pieces.chess3d

import moveGenerators.Direction3D
import moveGenerators.MoveGenerator3D
import pieces.Piece3D
import pieces.Royal
import players.Player

/**
 * Represents a King in 3D chess
 */

data class King3D(override val player: Player) : Piece3D, Royal {
    override val moveGenerators = listOf(MoveGenerator3D.Stepper3D(Direction3D.values().toList(), 1))

    override fun getSymbol(): String {
        return "K"
    }
}
