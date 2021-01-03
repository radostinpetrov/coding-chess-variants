package gameTypes.chess.rules

import coordinates.Coordinate2D
import gameMoves.GameMove2D
import gameMoves.GameMove2D.SimpleGameMove.BasicGameMove
import gameTypes.chess.AbstractChess
import pieces.chess.BlackPawn
import pieces.chess.WhitePawn
import players.Player
import kotlin.math.abs

class Enpassant : SpecialRules<AbstractChess> {
    override fun getPossibleMoves(game: AbstractChess, player: Player, moves: MutableList<GameMove2D>) {
        val board = game.board
        val moveLog = game.moveLog
        val res = mutableListOf<GameMove2D>()
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

        if (!(prevMove is BasicGameMove && ((prevMove.pieceMoved is WhitePawn || prevMove.pieceMoved is BlackPawn) && abs(prevMove.from.y - prevMove.to.y) == 2))) {
            return
        }
        val pawns = board.getPieces(player).filter { p -> (p.first is WhitePawn || p.first is BlackPawn) && (p.second.y == prevMove.to.y) && (abs(p.second.x - prevMove.to.x) == 1) }
        for (pawn in pawns) {
            val dy = if (pawn.first is BlackPawn) -1 else 1
            if (board.getPiece(Coordinate2D(prevMove.to.x, prevMove.to.y + dy)) != null) {
                continue
            }
            res.add(
                GameMove2D.CompositeGameMove(
                    listOf(
                        BasicGameMove(
                            Coordinate2D(pawn.second.x, pawn.second.y),
                            Coordinate2D(prevMove.to.x, prevMove.to.y), pawn.first, player, prevMove.pieceMoved, checkForCheck = false
                        ),
                        BasicGameMove(
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
