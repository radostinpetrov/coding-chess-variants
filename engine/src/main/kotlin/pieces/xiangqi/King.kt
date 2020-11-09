package main.kotlin.pieces.xiangqi

import main.kotlin.moves.Direction
import main.kotlin.moves.Move
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

class King(override val player: Player) : Piece {
    private val palaceX = listOf(3, 4, 5)
    private val palaceY = listOf(0, 1, 2, 7, 8, 9)

    override val moveTypes: List<Move>
        get() = listOf(
                Move.RestrictedDestination(Move.Stepper(Direction.NORTH_WEST, 1, true), palaceX, palaceY),
                Move.RestrictedDestination(Move.Stepper(Direction.NORTH, 1, true), palaceX, palaceY),
                Move.RestrictedDestination(Move.Stepper(Direction.NORTH_EAST, 1, true), palaceX, palaceY),
                Move.RestrictedDestination(Move.Stepper(Direction.EAST, 1, true), palaceX, palaceY),
                Move.RestrictedDestination(Move.Stepper(Direction.SOUTH_EAST, 1, true), palaceX, palaceY),
                Move.RestrictedDestination(Move.Stepper(Direction.SOUTH, 1, true), palaceX, palaceY),
                Move.RestrictedDestination(Move.Stepper(Direction.SOUTH_WEST, 1, true), palaceX, palaceY),
                Move.RestrictedDestination(Move.Stepper(Direction.WEST, 1, true), palaceX, palaceY),
        )

    override fun getSymbol(): String {
        return "K"
    }
}