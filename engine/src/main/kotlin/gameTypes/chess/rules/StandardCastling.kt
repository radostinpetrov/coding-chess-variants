package gameTypes.chess.rules

import coordinates.Coordinate2D
import gameMoves.GameMove2D
import gameMoves.GameMove2D.SimpleGameMove.BasicGameMove
import gameTypes.chess.StandardChess
import pieces.chess.King
import pieces.chess.Rook
import players.Player

class StandardCastling : SpecialRules<StandardChess> {
    override fun getPossibleMoves(game: StandardChess, player: Player, moves: MutableList<GameMove2D>) {
        val board = game.board
        val moveLog = game.moveLog
        val currentPlayerMoves = moveLog.filter { x -> x.player == player }
        val rooks = (board.getPieces(player).filter { p -> p.first.player == player && p.first is Rook }.toMutableList())

        for (move in currentPlayerMoves) {
            when (move) {
                is BasicGameMove -> {
                    if (move.pieceMoved is King) {
                        return
                    }
                    rooks.removeAll { it.first === move.pieceMoved }
                }
                is GameMove2D.CompositeGameMove -> {
                    for (basicMove in move.gameMoves) {
                        if (basicMove is BasicGameMove) {
                            if (basicMove.pieceMoved is King) {
                                return
                            }
                            rooks.removeAll { it.first === basicMove.pieceMoved }
                        }
                    }
                }
                else -> {
                    continue
                }
            }
        }

        val res = mutableListOf<GameMove2D>()
        val kingCoordinate = board.getPieces(player).find { p -> p.first.player == player && p.first is King }!!.second
        val king = game.board.getPiece(kingCoordinate) ?: return
        // Check Left for check
        var leftRook: Coordinate2D? = null
        var rightRook: Coordinate2D? = null
        for (rook in rooks) {
            if (rook.second.x == 0) {
                leftRook = rook.second
            }
            if (rook.second.x == board.m - 1) {
                rightRook = rook.second
            }
        }
        for (i in 1..3) {
            val toCheckCoordLeft = Coordinate2D(kingCoordinate.x - i, kingCoordinate.y)
            if (board.getPiece(toCheckCoordLeft) != null) {
                leftRook = null
            }
            val toCheckCoordRight = Coordinate2D(kingCoordinate.x + i, kingCoordinate.y)
            if (i != 3 && board.getPiece(toCheckCoordRight) != null) {
                rightRook = null
            }
        }

        if (leftRook != null) {
            val rook = board.getPiece(leftRook)
            res.add(
                GameMove2D.CompositeGameMove(
                    listOf(
                        BasicGameMove(
                            Coordinate2D(kingCoordinate.x, kingCoordinate.y),
                            Coordinate2D(kingCoordinate.x - 1, kingCoordinate.y), king, player
                        ),
                        BasicGameMove(
                            Coordinate2D(kingCoordinate.x - 1, kingCoordinate.y),
                            Coordinate2D(kingCoordinate.x - 2, kingCoordinate.y), king, player
                        ),
                        BasicGameMove(
                            Coordinate2D(leftRook.x, leftRook.y),
                            Coordinate2D(kingCoordinate.x - 1, kingCoordinate.y), rook!!, player
                        )
                    ),
                    player
                )
            )
        }
        if (rightRook != null) {
            val rook = board.getPiece(rightRook)
            res.add(
                GameMove2D.CompositeGameMove(
                    listOf(
                        BasicGameMove(
                            Coordinate2D(kingCoordinate.x, kingCoordinate.y),
                            Coordinate2D(kingCoordinate.x + 1, kingCoordinate.y), king, player
                        ),
                        BasicGameMove(
                            Coordinate2D(kingCoordinate.x + 1, kingCoordinate.y),
                            Coordinate2D(kingCoordinate.x + 2, kingCoordinate.y), king, player
                        ),
                        BasicGameMove(
                            Coordinate2D(rightRook.x, rightRook.y),
                            Coordinate2D(kingCoordinate.x + 1, kingCoordinate.y), rook!!, player
                        )
                    ),
                    player
                )
            )
        }
        moves.addAll(res)
    }
}
