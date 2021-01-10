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

enum class CastlingDirection {
    LEFT, RIGHT
}

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

    private fun canCastle(game: C, player: Player) : Boolean {
        val board = game.board
        val moveLog = game.moveLog
        val currentPlayerMoves = moveLog.filter { x -> x.player == player }
        val rooks = (board.getPieces(player).filter { p -> p.first.player == player && p.first is Rook }.toMutableList())

        for (move in currentPlayerMoves) {
            if (move.displayPieceMoved is King) {
                return false
            }
            rooks.removeAll { it.first === move.displayPieceMoved }
        }
        return rooks.isNotEmpty()
    }

    private fun getKing(game: C, player: Player): Pair<Piece2D, Coordinate2D> {
        return game.board.getPieces(player).find {
                p -> p.first.player == player && p.first is King }!!
    }

    open fun checkRooks(game: C, player: Player): Pair<Coordinate2D?, Coordinate2D?> {
        val board = game.board

        val king = getKing(game, player)
        val kingPiece = king.first
        val kingCoordinate = king.second

        val rooks : List<Pair<Piece2D, Coordinate2D>> = board.getPieces(player)
            .filter { p -> p.first.player == player && p.first is Rook }

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