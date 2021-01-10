package rules

import coordinates.Coordinate2D
import moves.Move
import moves.Move.SimpleMove.BasicMove
import gameTypes.chess.StandardChess
import pieces.chess.King
import pieces.chess.Rook
import players.Player

/**
 * Castling in standard chess.
 *
 * Moves the king two squares towards a rook on the player's first rank,
 * then moving the rook to the square that the king crossed.
 *
 * Can only occur provided all of the following conditions hold:
 *  1. The castling must be kingside or queenside
 *  2. Neither the king nor the chosen rook has previously moved.
 *  3. There are no pieces between the king and the chosen rook.
 *  4. The king is not currently in check.
 *  5. The king does not pass through a square that is attacked by an enemy piece.
 *  6. The king does not end up in check. (True of any legal move.)
 */
class StandardCastling(val p1CanCastleLeft: Boolean = true, val p1CanCastleRight: Boolean = true, val p2CanCastleLeft: Boolean = true, val p2CanCastleRight: Boolean = true) : SpecialRules2D<StandardChess> {
    override fun getPossibleMoves(game: StandardChess, player: Player, moves: MutableList<Move>) {
        val board = game.board
        val moveLog = game.moveLog
        val currentPlayerMoves = moveLog.filter { x -> x.player == player }
        val rooks = (board.getPieces(player).filter { p -> p.first.player == player && p.first is Rook }.toMutableList())

        val playerCanCastleLeft = if (player == game.players[0]) p1CanCastleLeft else p2CanCastleLeft
        val playerCanCastleRight = if (player == game.players[0]) p1CanCastleRight else p2CanCastleRight

        for (move in currentPlayerMoves) {
            when (move) {
                is BasicMove -> {
                    if (move.pieceMoved is King) {
                        return
                    }
                    rooks.removeAll { it.first === move.pieceMoved }
                }
                is Move.CompositeMove -> {
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

        val res = mutableListOf<Move>()
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

        if (playerCanCastleLeft && leftRook != null) {
            val rook = board.getPiece(leftRook)
            res.add(
                Move.CompositeMove(
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
                            Coordinate2D(leftRook.x, leftRook.y),
                            Coordinate2D(kingCoordinate.x - 1, kingCoordinate.y), rook!!, player
                        )
                    ),
                    player
                )
            )
        }
        if (playerCanCastleRight && rightRook != null) {
            val rook = board.getPiece(rightRook)
            res.add(
                Move.CompositeMove(
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
