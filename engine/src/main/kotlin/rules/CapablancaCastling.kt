package rules

import coordinates.Coordinate2D
import moves.Move2D
import moves.Move2D.SimpleMove.BasicMove
import gameTypes.chess.CapablancaChess
import pieces.chess.King
import pieces.chess.Rook
import players.Player

class CapablancaCastling : SpecialRules2D<CapablancaChess> {
    override fun getPossibleMoves(game: CapablancaChess, player: Player, moves: MutableList<Move2D>) {
        val board = game.board
        val moveLog = game.moveLog
        val currentPlayerMoves = moveLog.filter { x -> x.player == player }
        val rooks = (board.getPieces(player).filter { p -> p.first.player == player && p.first is Rook }.toMutableList())

        for (move in currentPlayerMoves) {
            when (move) {
                is BasicMove -> {
                    if (move.pieceMoved is King) {
                        return
                    }
                    rooks.removeAll { it.first === move.pieceMoved }
                }
                is Move2D.CompositeMove -> {
                    for (basicMove in move.moves) {
                        if (basicMove is BasicMove) {
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

        val res = mutableListOf<Move2D>()
        val king = board.getPieces(player).find { p -> p.first.player == player && p.first is King } ?: return
        val kingPiece = king.first
        val kingCoordinate = king.second

        // Check Left for check
        var leftRook: Coordinate2D? = null
        var rightRook: Coordinate2D? = null
        for (rook in rooks) {
            if (rook.second.x == 0) {
                leftRook = rook.second
            }
            if (rook.second.x == board.cols - 1) {
                rightRook = rook.second
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
                Move2D.CompositeMove(
                    listOf(
                        BasicMove(
                            Coordinate2D(kingCoordinate.x, kingCoordinate.y),
                            Coordinate2D(kingCoordinate.x - 1, kingCoordinate.y), kingPiece, player
                        ),
                        BasicMove(
                            Coordinate2D(kingCoordinate.x - 1, kingCoordinate.y),
                            Coordinate2D(kingCoordinate.x - 2, kingCoordinate.y), kingPiece, player
                        ),
                        BasicMove(
                            Coordinate2D(kingCoordinate.x - 2, kingCoordinate.y),
                            Coordinate2D(kingCoordinate.x - 3, kingCoordinate.y), kingPiece, player
                        ),
                        BasicMove(
                            Coordinate2D(leftRook.x, leftRook.y),
                            Coordinate2D(kingCoordinate.x - 2, kingCoordinate.y), rook!!, player
                        )
                    ),
                    player
                )
            )
        }
        if (rightRook != null) {
            val rook = board.getPiece(rightRook)
            res.add(
                Move2D.CompositeMove(
                    listOf(
                        BasicMove(
                            Coordinate2D(kingCoordinate.x, kingCoordinate.y),
                            Coordinate2D(kingCoordinate.x + 1, kingCoordinate.y), kingPiece, player
                        ),
                        BasicMove(
                            Coordinate2D(kingCoordinate.x + 1, kingCoordinate.y),
                            Coordinate2D(kingCoordinate.x + 2, kingCoordinate.y), kingPiece, player
                        ),
                        BasicMove(
                            Coordinate2D(kingCoordinate.x + 2, kingCoordinate.y),
                            Coordinate2D(kingCoordinate.x + 3, kingCoordinate.y), kingPiece, player
                        ),
                        BasicMove(
                            Coordinate2D(rightRook.x, rightRook.y),
                            Coordinate2D(kingCoordinate.x + 2, kingCoordinate.y), rook!!, player
                        )
                    ),
                    player
                )
            )
        }
        moves.addAll(res)
    }
}
