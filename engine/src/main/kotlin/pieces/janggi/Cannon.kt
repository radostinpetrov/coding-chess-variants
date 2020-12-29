package pieces.janggi

import coordinates.Coordinate2D
import moves.Move2D
import moves.region.BoxRegion
import moves.region.CompositeRegion
import pieces.Piece2D
import players.Player

data class Cannon(override val  player: Player) : Piece2D {
    private val palace1 = BoxRegion(Coordinate2D(3, 0), Coordinate2D(5, 2))
    private val palace2 = BoxRegion(Coordinate2D(3, 7), Coordinate2D(5, 9))
    private val palace = CompositeRegion(listOf(palace1, palace2))

    override val moveTypes: List<Move2D>
        get() = listOf(
            Move2D.Hopper(HV = true, canJumpOverSamePiece = false),
            Move2D.Restricted(Move2D.Hopper(D = true, canJumpOverSamePiece = false), palace)
        )

    override fun getSymbol(): String {
        return "C"
    }
}
