package pieces.janggi

import coordinates.Coordinate2D
import moveGenerators.MoveGenerator2D
import regions.BoxRegion
import regions.CoordinateRegion
import regions.CompositeRegion
import pieces.Piece2D
import players.Player

data class Chariot(override val player: Player) : Piece2D {
    private val palace1 = BoxRegion(Coordinate2D(3, 0), Coordinate2D(5, 2))
    private val palace2 = BoxRegion(Coordinate2D(3, 7), Coordinate2D(5, 9))
    private val palace = CompositeRegion(listOf(palace1, palace2))

    override val moveGenerators: List<MoveGenerator2D>
        get() = listOf(
            MoveGenerator2D.Slider(H = true, V = true, A = false, D = false),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(A = true, D = true), CoordinateRegion(4, 1)), palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(A = true, D = true), CoordinateRegion(4, 8)), palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(D = true), CoordinateRegion(3, 0)), palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(D = true), CoordinateRegion(3, 7)), palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(A = true), CoordinateRegion(3, 2)), palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(A = true), CoordinateRegion(3, 9)), palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(D = true), CoordinateRegion(5, 2)), palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(D = true), CoordinateRegion(5, 9)), palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(A = true), CoordinateRegion(5, 0)), palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(A = true), CoordinateRegion(5, 7)), palace),
        )

    override fun getSymbol(): String {
        return "R"
    }
}
