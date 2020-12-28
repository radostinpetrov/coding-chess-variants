package pieces.janggi

import Coordinate
import moves.Move
import moves.region.BoxRegion
import moves.region.CoordinateRegion
import moves.region.CompositeRegion
import pieces.Piece
import players.Player

data class Chariot(override val player: Player) : Piece {
    private val palace1 = BoxRegion(Coordinate(3, 0), Coordinate(5, 2))
    private val palace2 = BoxRegion(Coordinate(3, 7), Coordinate(5, 9))
    private val palace = CompositeRegion(listOf(palace1, palace2))

    override val moveTypes: List<Move>
        get() = listOf(
            Move.Slider(H = true, V = true, A = false, D = false),
            Move.RestrictedDestination(Move.Restricted(Move.Slider(A = true, D = true), CoordinateRegion(4, 1)), palace),
            Move.RestrictedDestination(Move.Restricted(Move.Slider(A = true, D = true), CoordinateRegion(4, 8)), palace),
            Move.RestrictedDestination(Move.Restricted(Move.Slider(D = true), CoordinateRegion(3, 0)), palace),
            Move.RestrictedDestination(Move.Restricted(Move.Slider(D = true), CoordinateRegion(3, 7)), palace),
            Move.RestrictedDestination(Move.Restricted(Move.Slider(A = true), CoordinateRegion(3, 2)), palace),
            Move.RestrictedDestination(Move.Restricted(Move.Slider(A = true), CoordinateRegion(3, 9)), palace),
            Move.RestrictedDestination(Move.Restricted(Move.Slider(D = true), CoordinateRegion(5, 2)), palace),
            Move.RestrictedDestination(Move.Restricted(Move.Slider(D = true), CoordinateRegion(5, 9)), palace),
            Move.RestrictedDestination(Move.Restricted(Move.Slider(A = true), CoordinateRegion(5, 0)), palace),
            Move.RestrictedDestination(Move.Restricted(Move.Slider(A = true), CoordinateRegion(5, 7)), palace),
        )

    override fun getSymbol(): String {
        return "R"
    }
}
