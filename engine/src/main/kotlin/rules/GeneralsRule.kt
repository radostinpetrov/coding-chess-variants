package rules

import coordinates.Coordinate2D
import moves.Move
import gameTypes.chess.AbstractChess
import pieces.Royal
import players.Player
import kotlin.math.max
import kotlin.math.min

/**
 * Flying general rule in Janggi and Xiangqi.
 *
 * The two generals cannot face each other along the same line
 * without any intervening pieces.
 */
class GeneralsRule : SpecialRules2D<AbstractChess> {
    override fun getPossibleMoves(game: AbstractChess, player: Player, moves: MutableList<Move>) {
        moves.retainAll { !generalsFaceEachOther(game, it) }
    }

    private fun generalsFaceEachOther(game: AbstractChess, move: Move): Boolean {
        game.makeMove(move)
        val generalCoordinates = game.board.getPieces().filter { it.first is Royal }
        if (generalCoordinates.size < 2) {
            game.undoMove()
            return false
        }
        val coordinate1 = generalCoordinates[0].second
        val coordinate2 = generalCoordinates[1].second

        if (coordinate1.x == coordinate2.x) {
            var blocked = false
            val x = coordinate1.x
            val start = min(coordinate1.y, coordinate2.y) + 1
            val end = max(coordinate1.y, coordinate2.y) - 1
            for (y in start..end) {
                if (game.board.getPiece(Coordinate2D(x, y)) != null) {
                    blocked = true
                }
            }
            game.undoMove()
            return !blocked
        }
        game.undoMove()
        return false
    }
}
