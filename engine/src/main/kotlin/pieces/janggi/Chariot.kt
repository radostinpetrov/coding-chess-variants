package pieces.janggi

import moveGenerators.MoveGenerator2D
import regions.CoordinateRegion
import pieces.Piece2D
import players.Player

/**
 * Represents a chariot in Janggi
 * equivalent to rook in standard chess, but can move diagonally within palace
 */
data class Chariot(override val player: Player) : Piece2D {

    override val moveGenerators: List<MoveGenerator2D> =
        listOf(
            MoveGenerator2D.Slider(H = true, V = true, A = false, D = false),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(A = true, D = true), CoordinateRegion(4, 1)), SpecialRegion.palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(A = true, D = true), CoordinateRegion(4, 8)), SpecialRegion.palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(D = true), CoordinateRegion(3, 0)), SpecialRegion.palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(D = true), CoordinateRegion(3, 7)), SpecialRegion.palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(A = true), CoordinateRegion(3, 2)), SpecialRegion.palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(A = true), CoordinateRegion(3, 9)), SpecialRegion.palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(D = true), CoordinateRegion(5, 2)), SpecialRegion.palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(D = true), CoordinateRegion(5, 9)), SpecialRegion.palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(A = true), CoordinateRegion(5, 0)), SpecialRegion.palace),
            MoveGenerator2D.RestrictedDestination(MoveGenerator2D.Restricted(MoveGenerator2D.Slider(A = true), CoordinateRegion(5, 7)), SpecialRegion.palace),
        )

    override fun getSymbol(): String {
        return "R"
    }
}
