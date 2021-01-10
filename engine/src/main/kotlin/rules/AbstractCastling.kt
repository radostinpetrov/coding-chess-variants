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
            when (move) {
                is BasicMove -> {
                    if (move.pieceMoved is King) {
                        return false
                    }
                    rooks.removeAll { it.first === move.pieceMoved }
                }
                is CompositeMove -> {
                    for (basicMove in move.moves) {
                        if (basicMove is BasicMove) {
                            if (basicMove.pieceMoved is King) {
                                return false
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
        if (rooks.isEmpty()) {
            return false
        }
        return true
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
//        var leftRook : Pair<Piece2D, Coordinate2D>? = null
//        var rightRook : Pair<Piece2D, Coordinate2D>? = null
//        for (rook in rooks) {
//            if (kingCoordinate.x < rook.second.x) {
//                rightRook = rook
//            } else {
//                leftRook = rook
//            }
//        }
//
//        if (leftRook != null) {
//            val lo = Math.min(castleWidth - 1, kingCoordinate.x)
//            val hi = Math.max(castleWidth - 1, kingCoordinate.x)
//            val rookPiece = leftRook.first
//
//            for (x in lo until hi) {
//                val piece = board.getPiece(Coordinate2D(x, kingCoordinate.y))
//                if (piece != null && piece != rookPiece && piece != kingPiece) {
//                    leftRook = null
//                    break
//                }
//            }
//        }
//
//        if (rightRook != null) {
//            val rookPiece = rightRook.first
//            for (x in kingCoordinate.x + 1 until board.cols - 1) {
//                val piece = board.getPiece(Coordinate2D(x, kingCoordinate.y))
//                if (piece != null && piece != rookPiece && piece != kingPiece) {
//                    rightRook = null
//                    break
//                }
//            }
//        }
//
//        return Pair(leftRook?.second, rightRook?.second)

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
        for (i in 1..castleWidth) {
            val toCheckCoordLeft = Coordinate2D(kingCoordinate.x - i, kingCoordinate.y)
            if (board.getPiece(toCheckCoordLeft) != null) {
                leftRook = null
            }
            val toCheckCoordRight = Coordinate2D(kingCoordinate.x + i, kingCoordinate.y)
            if (i != castleWidth && board.getPiece(toCheckCoordRight) != null) {
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
            val finalKingX = if (dir == CastlingDirection.LEFT) castleWidth - 1 else board.cols - castleWidth + 1
            val finalRookX = if (dir == CastlingDirection.LEFT) castleWidth else board.cols - castleWidth

            val range = if (kingCoordinate.x < finalKingX) kingCoordinate.x + 1 until finalKingX else kingCoordinate.x - 1 downTo finalKingX

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