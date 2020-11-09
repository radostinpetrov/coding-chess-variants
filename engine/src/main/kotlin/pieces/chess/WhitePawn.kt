package main.kotlin.pieces.chess

import main.kotlin.moves.Direction
import main.kotlin.moves.Move
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

open class WhitePawn(override val player: Player, val startingRow: Int) : Piece {
    override val moveTypes: List<Move>
        get() = listOf(
            Move.Restricted(Move.Stepper(Direction.NORTH, 2), listOf(), listOf(startingRow)),
            Move.AddForcedPromotion(
                Move.Stepper(Direction.NORTH, 1),
                listOf(),
                listOf(7),
                listOf(Queen(player), Bishop(player), Knight(player), Rook(player))
            ),
            Move.AddForcedPromotion(
                Move.CaptureOnly(Move.Stepper(Direction.NORTH_EAST, 1, true)),
                listOf(),
                listOf(7),
                listOf(Queen(player), Bishop(player), Knight(player), Rook(player))
            ),
            Move.AddForcedPromotion(
                Move.CaptureOnly(Move.Stepper(Direction.NORTH_WEST, 1, true)),
                listOf(),
                listOf(7),
                listOf(Queen(player), Bishop(player), Knight(player), Rook(player))
            ),
        )

    override fun getSymbol(): String {
        return "P"
    }
}