package gameTypes.xiangqi.rules

import Coordinate
import GameMove
import gameTypes.chess.AbstractChess
import gameTypes.chess.rules.SpecialRules
import pieces.King
import players.Player
import kotlin.math.max
import kotlin.math.min

class GeneralsRule : SpecialRules<AbstractChess> {
    override fun getPossibleMoves(game: AbstractChess, player: Player, moves: MutableList<GameMove>) {
        moves.retainAll { !generalsFaceEachOther(game, it) }
    }

    private fun generalsFaceEachOther(game: AbstractChess, move: GameMove): Boolean {
        game.makeMove(move)
        val generalCoordinates = game.board.getPieces().filter { it.first is King }
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
                if (game.board.getPiece(Coordinate(x, y)) != null) {
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
