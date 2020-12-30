package gameTypes.chess.rules

import coordinates.Coordinate2D
import gameMoves.GameMove2D
import gameMoves.GameMove2D.CompositeGameMove
import gameMoves.GameMove2D.SimpleGameMove.*
import gameTypes.chess.Chess960
import pieces.Piece2D
import pieces.chess.King
import pieces.chess.Rook
import players.Player

class Chess960Castling : SpecialRules<Chess960> {
    override fun getPossibleMoves(game: Chess960, player: Player, moves: MutableList<GameMove2D>) {
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
                is CompositeGameMove -> {
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

        val kingCoordinate = board.getPieces(player).find { p -> p.first.player == player && p.first is King }!!.second
        val king = game.board.getPiece(kingCoordinate) ?: return

        var leftRook: Pair<Piece2D, Coordinate2D>? = null
        var rightRook: Pair<Piece2D, Coordinate2D>? = null
        for (rook in rooks) {
            if (kingCoordinate.x < rook.second.x) {
                rightRook = rook
            } else {
                leftRook = rook
            }
        }

        val res = mutableListOf<GameMove2D>()

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
                if (piece != null && piece != leftRook!!.first && piece != king) {
                    leftRook = null
                    break
                }
            }
        }

        val leftRookSquarePiece = board.getPiece(Coordinate2D(3, kingCoordinate.y))
        if (leftRookSquarePiece != null && leftRookSquarePiece != king && leftRook != null && leftRookSquarePiece != leftRook.first) {
            leftRook = null
        }

        if (leftRook != null) {
            val rook = leftRook.first

            val castleList = mutableListOf<GameMove2D.SimpleGameMove>()
            castleList.add(
                RemovePieceGameMove(
                    player,
                    rook,
                    leftRook.second
                    )
            )

            var prevKingCoordinate = kingCoordinate
            for (i in range) {
                val newCoordinate = Coordinate2D(i, prevKingCoordinate.y)
                castleList.add(
                    BasicGameMove(
                        prevKingCoordinate,
                        newCoordinate, king, player)
                )
                prevKingCoordinate = newCoordinate
            }

            castleList.add(
                AddPieceGameMove(
                    player,
                    rook,
                    Coordinate2D(3, kingCoordinate.y)
                )
            )

            val move = CompositeGameMove(castleList, player)
            move.displayFrom = kingCoordinate
            move.displayTo = Coordinate2D(2, kingCoordinate.y)

            res.add(move)
        }

        //right castling
        if (rightRook != null) {
            for (i in (kingCoordinate.x + 1) until 7) {
                val piece = board.getPiece(Coordinate2D(i, kingCoordinate.y))
                if (piece != null && piece != rightRook!!.first && piece != king) {
                    rightRook = null
                    break
                }
            }
        }

        val rightRookSquarePiece = board.getPiece(Coordinate2D(5, kingCoordinate.y))
        if (rightRookSquarePiece != null && rightRookSquarePiece != king && rightRook != null && rightRookSquarePiece != rightRook.first) {
            rightRook = null
        }

        if (rightRook != null) {
            val rook = rightRook.first

            val castleList = mutableListOf<GameMove2D.SimpleGameMove>()

            castleList.add(
                RemovePieceGameMove(
                    player,
                    rook,
                    rightRook.second
                )
            )

            var prevKingCoordinate = kingCoordinate
            for (i in (kingCoordinate.x + 1) .. 6) {
                val newCoordinate = Coordinate2D(i, prevKingCoordinate.y)
                castleList.add(
                    BasicGameMove(
                        prevKingCoordinate,
                        newCoordinate, king, player
                    )
                )
                prevKingCoordinate = newCoordinate
            }

            castleList.add(
                AddPieceGameMove(
                    player,
                    rook,
                    Coordinate2D(5, kingCoordinate.y)
                )
            )

            val move = CompositeGameMove(castleList, player)
            move.displayFrom = kingCoordinate
            move.displayTo = Coordinate2D(6, kingCoordinate.y)

            res.add(move)
        }

        moves.addAll(res)
    }
}
