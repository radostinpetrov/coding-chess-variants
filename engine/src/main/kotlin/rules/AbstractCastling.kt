package rules

import coordinates.Coordinate2D
import gameTypes.chess.AbstractChess
import moves.Move2D
import moves.Move2D.CompositeMove
import moves.Move2D.SimpleMove.*
import pieces.Piece2D
import pieces.chess.King
import pieces.chess.Rook
import players.Player

/**
 * Represents the direction the castling may happen
 * LEFT represents queenside castling (castling with the queenside/left rook)
 * RIGHT represents kingside castling (castling with the kingside/right rook)
 */
enum class CastlingDirection {
    LEFT, RIGHT
}

/**
 * General Castling Logic
 *
 * @property castleWidth the length of castle (typically half of board size - 1)
 */
abstract class AbstractCastling<C: AbstractChess> (
    private val castleWidth: Int,
    private val p1CanCastleLeft: Boolean = true,
    private val p1CanCastleRight: Boolean = true,
    private val p2CanCastleLeft: Boolean = true,
    private val p2CanCastleRight: Boolean = true
    ) : SpecialRules2D<C> {

    override fun getPossibleMoves(game: C, player: Player, moves: MutableList<Move2D>) {
        if (!canCastle(game, player)) {
            return
        }

        val playerCanCastleLeft = if (player == game.players[0]) p1CanCastleLeft else p2CanCastleLeft
        val playerCanCastleRight = if (player == game.players[0]) p1CanCastleRight else p2CanCastleRight
        val rooks = checkRooks(game, player)
        if (playerCanCastleLeft) {
            castle(game, player, moves, rooks.first, CastlingDirection.LEFT)
        }
        if (playerCanCastleRight) {
            castle(game, player, moves, rooks.second, CastlingDirection.RIGHT)
        }
    }

    /**
     * Helper functions
     */

    private fun getKing(game: C, player: Player): Pair<Piece2D, Coordinate2D> {
        return game.board.getPieces(player)
            .find { p -> p.first.player == player && p.first is King }!!
    }

    private fun getRooks(game: C, player: Player): MutableList<Pair<Piece2D, Coordinate2D>> {
        return game.board.getPieces(player)
            .filter { p -> p.first.player == player && p.first is Rook }
            .toMutableList()
    }

    /**
     * Checks if king or rooks have previously moved
     *
     * @return false if king has moved or both rooks have moved,
     * hence castling is not possible
     */
    private fun canCastle(game: C, player: Player) : Boolean {
        val moveLog = game.moveLog
        val currentPlayerMoves = moveLog.filter { x -> x.player == player }
        val rooks = getRooks(game, player)

        for (move in currentPlayerMoves) {
            if (move.displayPieceMoved is King) {
                return false
            }
            rooks.removeAll { it.first === move.displayPieceMoved }
        }
        return rooks.isNotEmpty()
    }

    /**
     * Checks if there are no pieces between the king and rooks and
     * returns a pair of coordinates of left and right rooks available for castling
     * (set to null if the rook is not available)
     *
     * @return a pair of coordinates of left and right rooks
     */
    private fun checkRooks(game: C, player: Player): Pair<Coordinate2D?, Coordinate2D?> {
        val board = game.board

        val king = getKing(game, player)
        val kingPiece = king.first
        val kingCoordinate = king.second

        val rooks = getRooks(game, player)
        var leftRook: Coordinate2D? = null
        var rightRook: Coordinate2D? = null
        for (rook in rooks) {
            val rookCoordinate = rook.second
            if (rookCoordinate.x < kingCoordinate.x) {
                leftRook = rookCoordinate
            } else {
                rightRook = rookCoordinate
            }
        }

        // Check Left Castling
        if (leftRook != null) {
            val rookPiece = board.getPiece(leftRook)
            val lo = minOf(castleWidth - 1, kingCoordinate.x)
            val hi = maxOf(castleWidth - 1, kingCoordinate.x)
            for (x in lo until hi) {
                val piece = board.getPiece(Coordinate2D(x, kingCoordinate.y))
                if (piece != null && piece != rookPiece && piece != kingPiece) {
                    leftRook = null
                    break
                }
            }

            val leftRookFinalDest = board.getPiece(Coordinate2D(castleWidth, kingCoordinate.y))
            if (leftRookFinalDest != null && leftRook != null &&
                leftRookFinalDest != kingPiece && leftRookFinalDest != rookPiece) {
                leftRook = null
            }
        }

        // Check Right Castling
        if (rightRook != null) {
            val rookPiece = board.getPiece(rightRook)
            for (x in kingCoordinate.x + 1 until board.cols - 1) {
                val piece = board.getPiece(Coordinate2D(x, kingCoordinate.y))
                if (piece != null && piece != rookPiece && piece != kingPiece) {
                    rightRook = null
                    break
                }
            }

            val rightRookFinalDest = board.getPiece(Coordinate2D(5, kingCoordinate.y))
            if (rightRookFinalDest != null && rightRook != null &&
                rightRookFinalDest != kingPiece && rightRookFinalDest != rookPiece) {
                rightRook = null
            }
        }

        return Pair(leftRook, rightRook)
    }

    /**
     * Generates an appropriate moves to carry out castling and add to moves.
     * Assumes that it satisfies the pre-conditions for castling.
     */
    private fun castle(game: C, player: Player, moves: MutableList<Move2D>, rookCoordinate: Coordinate2D?, dir: CastlingDirection) {
        val board = game.board
        val king = getKing(game, player)
        val kingPiece = king.first
        val kingCoordinate = king.second

        if (rookCoordinate != null) {
            val rook = board.getPiece(rookCoordinate)!!
            val castlingMoves = mutableListOf<Move2D.SimpleMove>()

            castlingMoves.add(RemovePieceMove(player, rook, rookCoordinate))

            val y = kingCoordinate.y
            var currCoordinate = kingCoordinate
            val finalKingX: Int
            val finalRookX: Int
            if (dir == CastlingDirection.LEFT) {
                finalKingX = castleWidth - 1
                finalRookX = castleWidth
            } else {
                finalKingX = board.cols - (castleWidth - 1)
                finalRookX = board.cols - castleWidth
            }

            val range = if (kingCoordinate.x < finalKingX) {
                kingCoordinate.x + 1 until finalKingX + 1
            } else {
                kingCoordinate.x - 1 downTo finalKingX
            }

            for (x in range) {
                val nextCoordinate = Coordinate2D(x, y)
                castlingMoves.add(
                    BasicMove(currCoordinate, nextCoordinate, kingPiece, player)
                )
                currCoordinate = nextCoordinate
            }

            castlingMoves.add(AddPieceMove(player, rook, Coordinate2D(finalRookX, y)))

            val move = CompositeMove(castlingMoves, player)
            move.displayPieceMoved = kingPiece
            move.displayFrom = kingCoordinate
            move.displayTo = Coordinate2D(finalKingX, y)
            moves.add(move)
        }
    }
}