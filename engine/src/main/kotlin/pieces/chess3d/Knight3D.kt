package pieces.chess3d

import moveGenerators.MoveGenerator3D
import pieces.Piece3D
import players.Player

/**
 * Represents a Knight in 3D chess
 */

data class Knight3D(override val player: Player) : Piece3D {
    override val moveGenerators = listOf(MoveGenerator3D.Leaper3D(0, 1, 2))

    override fun getSymbol(): String {
        return "N"
    }
}
