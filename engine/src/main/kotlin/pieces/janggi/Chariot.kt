package pieces.janggi

import moves.Move
import pieces.Piece
import players.Player

data class Chariot(override val player: Player) : Piece {
    private val palaceX = listOf(3, 4, 5)
    private val palaceY = listOf(0, 1, 2, 7, 8, 9)

    override val moveTypes: List<Move>
        get() = listOf(
            Move.Slider(H = true, V = true, A = false, D = false),
            Move.RestrictedDestination(Move.Restricted(Move.Slider(A = true, D = true), listOf(4), listOf(1, 8)), palaceX, palaceY),
            Move.RestrictedDestination(Move.Restricted(Move.Slider(D = true), listOf(3), listOf(0, 7)), palaceX, palaceY),
            Move.RestrictedDestination(Move.Restricted(Move.Slider(A = true), listOf(3), listOf(2, 9)), palaceX, palaceY),
            Move.RestrictedDestination(Move.Restricted(Move.Slider(D = true), listOf(5), listOf(2, 9)), palaceX, palaceY),
            Move.RestrictedDestination(Move.Restricted(Move.Slider(A = true), listOf(5), listOf(0, 7)), palaceX, palaceY),
        )

    override fun getSymbol(): String {
        return "R"
    }
}
