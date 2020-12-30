package gameTypes.chess.rules

import coordinates.Coordinate2D
import gameMoves.GameMove2D
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
                is GameMove2D.BasicGameMove -> {
                    if (move.pieceMoved is King) {
                        return
                    }
                    rooks.removeAll { it.first === move.pieceMoved }
                }
                is GameMove2D.CompositeGameMove -> {
                    for (basicMove in move.gameMoves) {
                        if (basicMove.pieceMoved is King) {
                            return
                        }
                        rooks.removeAll { it.first === basicMove.pieceMoved }
                    }
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
            range = lo .. hi
        } else {
            hi = kingCoordinate.x
            lo = 2
            range = hi downTo lo
        }

        if (leftRook != null) {
            for (i in lo until hi) {
                val piece = board.getPiece(Coordinate2D(i, kingCoordinate.y))
                if (piece != null && piece != leftRook!!.first) {
                    leftRook = null
                    break
                }
            }
        }

        if (board.getPiece(Coordinate2D(3, kingCoordinate.y)) != null) {
            leftRook = null
        }

        if (leftRook != null) {
            val rook = leftRook.first

            val castleList = mutableListOf<GameMove2D.BasicGameMove>()
            var prevKingCoordinate = kingCoordinate

            for (i in range) {
                val newCoordinate = Coordinate2D(i, prevKingCoordinate.y)

                if (newCoordinate != leftRook.second) {
                    castleList.add(
                        GameMove2D.BasicGameMove(
                            prevKingCoordinate,
                            newCoordinate, king, player)
                    )
                    prevKingCoordinate = newCoordinate
                }
            }

            castleList.add(
                GameMove2D.BasicGameMove(
                    leftRook.second,
                    Coordinate2D(3, kingCoordinate.y), rook, player)
            )

            res.add(
                GameMove2D.CompositeGameMove(
                    castleList,
                    player
                )
            )
        }

        //right castling
        if (rightRook != null) {
            for (i in (kingCoordinate.x + 1) until 7) {
                val piece = board.getPiece(Coordinate2D(i, kingCoordinate.y))
                if (piece != null && piece != rightRook!!.first) {
                    rightRook = null
                    break
                }
            }
        }

        if (board.getPiece(Coordinate2D(5, kingCoordinate.y)) != null) {
            rightRook = null
        }

        if (rightRook != null) {
            val rook = rightRook.first

            val castleList = mutableListOf<GameMove2D.BasicGameMove>()

            var prevKingCoordinate = kingCoordinate

            for (i in (kingCoordinate.x + 1) .. 6) {
                val newCoordinate = Coordinate2D(i, prevKingCoordinate.y)
                if (newCoordinate != rightRook.second) {
                    castleList.add(
                        GameMove2D.BasicGameMove(
                            prevKingCoordinate,
                            newCoordinate, king, player)
                    )
                    prevKingCoordinate = newCoordinate
                } else {
                    println("hello")
                }
            }

            castleList.add(
                GameMove2D.BasicGameMove(
                    rightRook.second,
                    Coordinate2D(5, kingCoordinate.y), rook, player)
            )

            res.add(
                GameMove2D.CompositeGameMove(
                    castleList,
                    player
                )
            )
        }

        moves.addAll(res)
    }
}
