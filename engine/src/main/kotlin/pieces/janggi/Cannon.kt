package pieces.janggi

import Coordinate
import moves.Move
import moves.region.BoxRegion
import moves.region.CompositeRegion
import pieces.Piece
import players.Player

data class Cannon(override val  player: Player) : Piece {
    private val palace1 = BoxRegion(Coordinate(3, 0), Coordinate(5, 2))
    private val palace2 = BoxRegion(Coordinate(3, 7), Coordinate(5, 9))
    private val palace = CompositeRegion(listOf(palace1, palace2))

    override val moveTypes: List<Move>
        get() = listOf(
            Move.Hopper(HV = true, canJumpOverSamePiece = false),
            Move.Restricted(Move.Hopper(D = true, canJumpOverSamePiece = false), palace)
        )

    override fun getSymbol(): String {
        return "C"
    }
}
