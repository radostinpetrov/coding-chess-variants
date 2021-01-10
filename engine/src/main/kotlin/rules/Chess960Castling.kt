package rules

import coordinates.Coordinate2D
import moves.Move
import moves.Move.CompositeMove
import moves.Move.SimpleMove.*
import gameTypes.chess.Chess960
import pieces.Piece2D
import pieces.chess.King
import pieces.chess.Rook
import players.Player

/**
 * Castling in chess 960
 */
class Chess960Castling : SpecialRules2D<Chess960> {
    override fun getPossibleMoves(game: Chess960, player: Player, moves: MutableList<Move>) {
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
                is CompositeMove -> {
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

        val king = board.getPieces(player).find { p -> p.first.player == player && p.first is King } ?: return
        val kingPiece = king.first
        val kingCoordinate = king.second

        var leftRook: Pair<Piece2D, Coordinate2D>? = null
        var rightRook: Pair<Piece2D, Coordinate2D>? = null
        for (rook in rooks) {
            if (kingCoordinate.x < rook.second.x) {
                rightRook = rook
            } else {
                leftRook = rook
            }
        }

        val res = mutableListOf<Move>()

        //left castling
        val hi: Int
        val lo: Int
        val range: IntProgression
        if (kingCoordinate.x < 2) {
            hi = 2
            lo = kingCoordinate.x
            range = (lo + 1) .. hi
        } else {
            hi = kingCoordinate.x
            lo = 2
            range = (hi - 1) downTo lo
        }

        if (leftRook != null) {
            for (i in lo until hi) {
                val piece = board.getPiece(Coordinate2D(i, kingCoordinate.y))
                if (piece != null && piece != leftRook!!.first && piece != kingPiece) {
                    leftRook = null
                    break
                }
            }
        }

        val leftRookSquarePiece = board.getPiece(Coordinate2D(3, kingCoordinate.y))
        if (leftRookSquarePiece != null && leftRookSquarePiece != kingPiece && leftRook != null && leftRookSquarePiece != leftRook.first) {
            leftRook = null
        }

        if (leftRook != null) {
            val rook = leftRook.first

            val castleList = mutableListOf<Move.SimpleMove>()
            castleList.add(
                RemovePieceMove(
                    player,
                    rook,
                    leftRook.second
                    )
            )

            var prevKingCoordinate = kingCoordinate
            for (i in range) {
                val newCoordinate = Coordinate2D(i, prevKingCoordinate.y)
                castleList.add(
                    BasicMove(
                        prevKingCoordinate,
                        newCoordinate, kingPiece, player)
                )
                prevKingCoordinate = newCoordinate
            }

            castleList.add(
                AddPieceMove(
                    player,
                    rook,
                    Coordinate2D(3, kingCoordinate.y)
                )
            )

            val move = CompositeMove(castleList, player)
            move.displayFrom = kingCoordinate
            move.displayTo = Coordinate2D(2, kingCoordinate.y)

            res.add(move)
        }

        //right castling
        if (rightRook != null) {
            for (i in (kingCoordinate.x + 1) until 7) {
                val piece = board.getPiece(Coordinate2D(i, kingCoordinate.y))
                if (piece != null && piece != rightRook!!.first && piece != kingPiece) {
                    rightRook = null
                    break
                }
            }
        }

        val rightRookSquarePiece = board.getPiece(Coordinate2D(5, kingCoordinate.y))
        if (rightRookSquarePiece != null && rightRookSquarePiece != kingPiece && rightRook != null && rightRookSquarePiece != rightRook.first) {
            rightRook = null
        }

        if (rightRook != null) {
            val rook = rightRook.first

            val castleList = mutableListOf<Move.SimpleMove>()

            castleList.add(
                RemovePieceMove(
                    player,
                    rook,
                    rightRook.second
                )
            )

            var prevKingCoordinate = kingCoordinate
            for (i in (kingCoordinate.x + 1) .. 6) {
                val newCoordinate = Coordinate2D(i, prevKingCoordinate.y)
                castleList.add(
                    BasicMove(
                        prevKingCoordinate,
                        newCoordinate, kingPiece, player
                    )
                )
                prevKingCoordinate = newCoordinate
            }

            castleList.add(
                AddPieceMove(
                    player,
                    rook,
                    Coordinate2D(5, kingCoordinate.y)
                )
            )

            val move = CompositeMove(castleList, player)
            move.displayFrom = kingCoordinate
            move.displayTo = Coordinate2D(6, kingCoordinate.y)

            res.add(move)
        }

        moves.addAll(res)
    }
}
