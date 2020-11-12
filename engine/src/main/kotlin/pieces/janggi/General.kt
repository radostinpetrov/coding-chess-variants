package main.kotlin.pieces.janggi

import main.kotlin.moves.Direction
import main.kotlin.moves.Move
import main.kotlin.pieces.King
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

class General(override val player: Player) : Piece, King {
    private val palaceX = listOf(3, 4, 5)
    private val palaceY = listOf(0, 1, 2, 7, 8, 9)

    override val moveTypes: List<Move>
        get() = listOf(
            Move.RestrictedDestination(Move.Stepper(Direction.NORTH, 1, true), palaceX, palaceY),
            Move.RestrictedDestination(Move.Stepper(Direction.EAST, 1, true), palaceX, palaceY),
            Move.RestrictedDestination(Move.Stepper(Direction.SOUTH, 1, true), palaceX, palaceY),
            Move.RestrictedDestination(Move.Stepper(Direction.WEST, 1, true), palaceX, palaceY),
            Move.Restricted(Move.Leaper(1, 1), listOf(4), listOf(1, 8)),
            Move.Restricted(Move.Stepper(Direction.NORTH_EAST, 1, true), listOf(3), listOf(7)),
            Move.Restricted(Move.Stepper(Direction.NORTH_WEST, 1, true), listOf(5), listOf(7)),
            Move.Restricted(Move.Stepper(Direction.SOUTH_EAST, 1, true), listOf(3), listOf(9)),
            Move.Restricted(Move.Stepper(Direction.SOUTH_WEST, 1, true), listOf(5), listOf(9)),
            Move.Restricted(Move.Stepper(Direction.SOUTH_EAST, 1, true), listOf(3), listOf(2)),
            Move.Restricted(Move.Stepper(Direction.SOUTH_WEST, 1, true), listOf(5), listOf(2)),
            Move.Restricted(Move.Stepper(Direction.NORTH_EAST, 1, true), listOf(3), listOf(0)),
            Move.Restricted(Move.Stepper(Direction.NORTH_WEST, 1, true), listOf(5), listOf(0)),
        )

    override fun getSymbol(): String {
        return "K"
    }
}
