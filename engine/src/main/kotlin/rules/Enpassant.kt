package rules

import coordinates.Coordinate2D
import moves.*
import gameTypes.chess.AbstractChess2D
import pieces.chess.BlackPawn
import pieces.chess.WhitePawn
import players.Player
import kotlin.math.abs

/**
 * En Passant in standard chess
 *
 * Special pawn capture that can only occur immediately after a pawn makes
 * a move of two squares from its starting square,
 * and it could have been captured by an enemy pawn had it advanced only one square
 */
class Enpassant : SpecialRules2D<AbstractChess2D> {
    override fun getPossibleMoves(game: AbstractChess2D, player: Player, moves: MutableList<Move2D>) {
        val board = game.board
        val moveLog = game.moveLog
        val res = mutableListOf<Move2D>()
        val prevMove = if (game.getCurrentPlayer() == player) {
            if (moveLog.isEmpty()) {
                return
            }
            moveLog[moveLog.size - 1]
        } else {
            if (moveLog.size <= 1) {
                return
            }
            moveLog[moveLog.size - 2]
        }

        if (!(prevMove is BasicMove2D && ((prevMove.pieceMoved is WhitePawn || prevMove.pieceMoved is BlackPawn) && abs(prevMove.from.y - prevMove.to.y) == 2))) {
            return
        }
        val pawns = board.getPieces(player).filter { p -> (p.first is WhitePawn || p.first is BlackPawn) && (p.second.y == prevMove.to.y) && (abs(p.second.x - prevMove.to.x) == 1) }
        for (pawn in pawns) {
            val dy = if (pawn.first is BlackPawn) -1 else 1
            if (board.getPiece(Coordinate2D(prevMove.to.x, prevMove.to.y + dy)) != null) {
                continue
            }
            res.add(
                CompositeMove2D(
                    listOf(
                        BasicMove2D(
                            Coordinate2D(pawn.second.x, pawn.second.y),
                            Coordinate2D(prevMove.to.x, prevMove.to.y), pawn.first, player, prevMove.pieceMoved, checkForCheck = false
                        ),
                        BasicMove2D(
                            Coordinate2D(prevMove.to.x, prevMove.to.y),
                            Coordinate2D(prevMove.to.x, prevMove.to.y + dy), pawn.first, player
                        )
                    ),
                    player
                )
            )
        }
        moves.addAll(res)
    }
}
