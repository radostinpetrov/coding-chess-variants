package boards

import Coordinate
import pieces.Piece
import players.Player

class Board2D(val n: Int, val m: Int): Board<Piece> {
    var board:Array<Array<Piece?>> = Array(n) { Array(m) { null } }
    override fun getBoardState(): Array<Array<Piece?>> {
        return board
    }

    override fun getPieces(): List<Pair<Piece, Coordinate>> {
        val res = mutableListOf<Pair<Piece, Coordinate>>()
        for (i in 0 until n) {
            for (j in 0 until m) {
                val c = Coordinate(j,i)
                val p = getPiece(c)
                if (p != null) {
                    res.add(Pair(p, c))
                }
            }
        }
        return res
    }

    override fun getPieces(player: Player): List<Pair<Piece, Coordinate>> {
        return getPieces().filter { p -> p.first.player == player }
    }

    override fun getPiece(coordinate: Coordinate): Piece? {
        if (!isInBounds(coordinate)) {
            return null
        }
        return board[coordinate.y][coordinate.x]
    }

    override fun getPieceCoordinate(piece: Piece): Coordinate {
        TODO("Not yet implemented")
    }

    override fun addPiece(coordinate: Coordinate, piece: Piece) {
        if (!isInBounds(coordinate)) {
            throw ArrayIndexOutOfBoundsException()
        }
        board[coordinate.y][coordinate.x] = piece
    }

    override fun removePiece(coordinate: Coordinate, piece: Piece) {
        if (!isInBounds(coordinate)) {
            throw ArrayIndexOutOfBoundsException()
        }
        board[coordinate.y][coordinate.x] = null
    }

    fun isInBounds(coordinate: Coordinate): Boolean {
        return (coordinate.x >= 0) && (coordinate.y >= 0) && (coordinate.x < m) && (coordinate.y < n)
    }
}