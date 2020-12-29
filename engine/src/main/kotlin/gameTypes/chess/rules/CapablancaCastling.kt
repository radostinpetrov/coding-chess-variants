package gameTypes.chess.rules

import coordinates.Coordinate2D
import gameMoves.GameMove2D
import gameTypes.chess.CapablancaChess
import pieces.chess.King
import pieces.chess.Rook
import players.Player

class CapablancaCastling : SpecialRules<CapablancaChess> {
    override fun getPossibleMoves(game: CapablancaChess, player: Player, moves: MutableList<GameMove2D>) {
        val board = game.board
        val moveLog = game.moveLog
        val currentPlayerMoves = moveLog.filter { x -> x.player == player }
        val rooks = (board.getPieces(player).filter { p -> p.first.player == player && p.first is Rook }.associateBy({ it.first }, { it.second })).toMutableMap()

        val res = mutableListOf<GameMove2D>()
        for (move in currentPlayerMoves) {
            when (move) {
                is GameMove2D.BasicGameMove -> {
                    if (move.pieceMoved is King) {
                        return
                    }
                    if (rooks.contains(move.pieceMoved)) {
                        rooks.remove(move.pieceMoved)
                    }
                }
                is GameMove2D.CompositeGameMove -> {
                    for (basicMove in move.gameMoves) {
                        if (basicMove.pieceMoved is King) {
                            return
                        }
                        if (rooks.contains(basicMove.pieceMoved)) {
                            rooks.remove(basicMove.pieceMoved)
                        }
                    }
                }
            }
        }

        val kingCoordinate = board.getPieces(player).find { p -> p.first.player == player && p.first is King }!!.second
        val king = game.board.getPiece(kingCoordinate) ?: return
        // Check Left for check
        var leftRook: Coordinate2D? = null
        var rightRook: Coordinate2D? = null
        for (rook in rooks) {
            if (rook.value.x == 0) {
                leftRook = rook.value
            }
            if (rook.value.x == board.m - 1) {
                rightRook = rook.value
            }
        }
        for (i in 1..4) {
            val toCheckCoordLeft = Coordinate2D(kingCoordinate.x - i, kingCoordinate.y)
            if (board.getPiece(toCheckCoordLeft) != null) {
                leftRook = null
            }
            val toCheckCoordRight = Coordinate2D(kingCoordinate.x + i, kingCoordinate.y)
            if (i != 4 && board.getPiece(toCheckCoordRight) != null) {
                rightRook = null
            }
        }
        if (leftRook != null) {
            val rook = board.getPiece(leftRook)
            res.add(
                GameMove2D.CompositeGameMove(
                    listOf(
                        GameMove2D.BasicGameMove(
                            Coordinate2D(kingCoordinate.x, kingCoordinate.y),
                            Coordinate2D(kingCoordinate.x - 1, kingCoordinate.y), king, player),
                        GameMove2D.BasicGameMove(
                            Coordinate2D(kingCoordinate.x - 1, kingCoordinate.y),
                            Coordinate2D(kingCoordinate.x - 2, kingCoordinate.y), king, player),
                        GameMove2D.BasicGameMove(
                            Coordinate2D(kingCoordinate.x - 2, kingCoordinate.y),
                            Coordinate2D(kingCoordinate.x - 3, kingCoordinate.y), king, player),
                        GameMove2D.BasicGameMove(
                            Coordinate2D(leftRook.x, leftRook.y),
                            Coordinate2D(kingCoordinate.x - 2, kingCoordinate.y), rook!!, player)
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
                        GameMove2D.BasicGameMove(
                            Coordinate2D(kingCoordinate.x, kingCoordinate.y),
                            Coordinate2D(kingCoordinate.x + 1, kingCoordinate.y), king, player),
                        GameMove2D.BasicGameMove(
                            Coordinate2D(kingCoordinate.x + 1, kingCoordinate.y),
                            Coordinate2D(kingCoordinate.x + 2, kingCoordinate.y), king, player),
                        GameMove2D.BasicGameMove(
                            Coordinate2D(kingCoordinate.x + 2, kingCoordinate.y),
                            Coordinate2D(kingCoordinate.x + 3, kingCoordinate.y), king, player),
                        GameMove2D.BasicGameMove(
                            Coordinate2D(rightRook.x, rightRook.y),
                            Coordinate2D(kingCoordinate.x + 2, kingCoordinate.y), rook!!, player)
                    ),
                    player
                )
            )
        }
        moves.addAll(res)
    }
}
