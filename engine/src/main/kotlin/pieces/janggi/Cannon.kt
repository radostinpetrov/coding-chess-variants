package pieces.janggi

import coordinates.Coordinate2D
import moveGenerators.MoveGenerator2D
import regions.BoxRegion
import regions.CompositeRegion
import pieces.Piece2D
import players.Player

data class Cannon(override val  player: Player) : Piece2D {
    private val palace1 = BoxRegion(Coordinate2D(3, 0), Coordinate2D(5, 2))
    private val palace2 = BoxRegion(Coordinate2D(3, 7), Coordinate2D(5, 9))
    private val palace = CompositeRegion(listOf(palace1, palace2))

    override val moveGenerators =
        listOf(
            MoveGenerator2D.Hopper(HV = true, canJumpOverSamePiece = false),
            MoveGenerator2D.Restricted(MoveGenerator2D.Hopper(D = true, canJumpOverSamePiece = false), palace)
        )

    override fun getSymbol(): String {
        return "C"
    }
}
