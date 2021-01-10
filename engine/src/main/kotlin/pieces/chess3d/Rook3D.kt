package pieces.chess3d

import moveGenerators.MoveGenerator3D
import pieces.Piece3D
import players.Player

data class Rook3D(override val player: Player) : Piece3D {
    override val moveGenerators = listOf(MoveGenerator3D.Slider3D(X = true, Y = true, Z = true))

    override fun getSymbol(): String {
        return "R"
    }
}