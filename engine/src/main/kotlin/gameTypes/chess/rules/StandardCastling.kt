package gameTypes.chess.rules

import Coordinate
import GameMove
import gameTypes.chess.StandardChess
import pieces.chess.King
import pieces.chess.Rook
import players.Player

class StandardCastling : SpecialRules<StandardChess> {
    override fun getPossibleMoves(game: StandardChess, player: Player, moves: MutableList<GameMove>) {
        val board = game.board
        val moveLog = game.moveLog
        val currentPlayerMoves = moveLog.filter { x -> x.player == player }
//        val rooks = (board.getPieces(player).filter { p -> p.first.player == player && p.first is Rook }.associateBy({ it.first }, { it.second })).toMutableMap()
        val rooks = (board.getPieces(player).filter { p -> p.first.player == player && p.first is Rook }.toMutableList())

        val res = mutableListOf<GameMove>()
        for (move in currentPlayerMoves) {
            when (move) {
                is GameMove.BasicGameMove -> {
                    if (move.pieceMoved is King) {
                        return
                    }
                    rooks.removeAll { it.first === move.pieceMoved }
//                    if (rooks.contains(move.pieceMoved)) {
//                        rooks.remove(move.pieceMoved)
//                    }
                }
                is GameMove.CompositeGameMove -> {
                    for (basicMove in move.gameMoves) {
                        if (basicMove.pieceMoved is King) {
                            return
                        }
                        rooks.removeAll { it.first === basicMove.pieceMoved }
//                        if (rooks.contains(basicMove.pieceMoved)) {
//                            rooks.remove(basicMove.pieceMoved)
//                        }
                    }
                }
            }
        }

        val kingCoordinate = board.getPieces(player).find { p -> p.first.player == player && p.first is King }!!.second
        val king = game.board.getPiece(kingCoordinate) ?: return
        // Check Left for check
        var leftRook: Coordinate? = null
        var rightRook: Coordinate? = null
        for (rook in rooks) {
            if (rook.second.x == 0) {
                leftRook = rook.second
            }
            if (rook.second.x == board.m - 1) {
                rightRook = rook.second
            }
        }
        for (i in 1..3) {
            val toCheckCoordLeft = Coordinate(kingCoordinate.x - i, kingCoordinate.y)
            if (board.getPiece(toCheckCoordLeft) != null) {
                leftRook = null
            }
            val toCheckCoordRight = Coordinate(kingCoordinate.x + i, kingCoordinate.y)
            if (i != 3 && board.getPiece(toCheckCoordRight) != null) {
                rightRook = null
            }
        }
        if (leftRook != null) {
            val rook = board.getPiece(leftRook)
            res.add(
                GameMove.CompositeGameMove(
                    listOf(
                        GameMove.BasicGameMove(
                            Coordinate(kingCoordinate.x, kingCoordinate.y),
                            Coordinate(kingCoordinate.x - 1, kingCoordinate.y), king, player
                        ),
                        GameMove.BasicGameMove(
                            Coordinate(kingCoordinate.x - 1, kingCoordinate.y),
                            Coordinate(kingCoordinate.x - 2, kingCoordinate.y), king, player
                        ),
                        GameMove.BasicGameMove(
                            Coordinate(leftRook.x, leftRook.y),
                            Coordinate(kingCoordinate.x - 1, kingCoordinate.y), rook!!, player
                        )
                    ),
                    player
                )
            )
        }
        if (rightRook != null) {
            val rook = board.getPiece(rightRook)
            res.add(
                GameMove.CompositeGameMove(
                    listOf(
                        GameMove.BasicGameMove(
                            Coordinate(kingCoordinate.x, kingCoordinate.y),
                            Coordinate(kingCoordinate.x + 1, kingCoordinate.y), king, player
                        ),
                        GameMove.BasicGameMove(
                            Coordinate(kingCoordinate.x + 1, kingCoordinate.y),
                            Coordinate(kingCoordinate.x + 2, kingCoordinate.y), king, player
                        ),
                        GameMove.BasicGameMove(
                            Coordinate(rightRook.x, rightRook.y),
                            Coordinate(kingCoordinate.x + 1, kingCoordinate.y), rook!!, player
                        )
                    ),
                    player
                )
            )
        }
        moves.addAll(res)
    }
}
