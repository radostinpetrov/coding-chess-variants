package boards

import coordinates.Coordinate2D
import gameMoves.GameMove2D
import moves.Move2D
import pieces.Piece2D
import players.Player
import java.lang.Exception

class Board2D(val rows: Int, val cols: Int) : Board<Board2D, Move2D, GameMove2D.BasicGameMove, Piece2D, Coordinate2D> {
    private var board: Array<Array<Piece2D?>> = Array(rows) { Array(cols) { null } }
    override fun getBoardState(): Array<Array<Piece2D?>> {
        return board
    }

    override fun getPieces(): List<Pair<Piece2D, Coordinate2D>> {
        val res = mutableListOf<Pair<Piece2D, Coordinate2D>>()
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                val c = Coordinate2D(j, i)
                val p = getPiece(c)
                if (p != null) {
                    res.add(Pair(p, c))
                }
            }
        }
        return res
    }

    override fun getPieces(player: Player): List<Pair<Piece2D, Coordinate2D>> {
        return getPieces().filter { p -> p.first.player === player }
    }

    override fun getPiece(coordinate: Coordinate2D): Piece2D? {
        if (!isInBounds(coordinate)) {
            return null
        }
        return board[coordinate.y][coordinate.x]
    }

    override fun getPieceCoordinate(piece: Piece2D): Coordinate2D? {
        for (y in 0 until rows) {
            for (x in 0 until cols) {
                if (piece === board[y][x]) {
                    return Coordinate2D(x, y)
                }
            }
        }
        return null
    }

    override fun addPiece(coordinate: Coordinate2D, piece: Piece2D) {
        if (!isInBounds(coordinate)) {
            throw ArrayIndexOutOfBoundsException()
        }
        board[coordinate.y][coordinate.x] = piece
    }

    override fun removePiece(coordinate: Coordinate2D, piece: Piece2D) {
        if (!isInBounds(coordinate)) {
            throw ArrayIndexOutOfBoundsException()
        }
        if (board[coordinate.y][coordinate.x] != piece) {
            throw Exception("Cannot remove non-existing piece")
        }
        board[coordinate.y][coordinate.x] = null
    }

    fun isInBounds(coordinate: Coordinate2D): Boolean {
        return (coordinate.x >= 0) && (coordinate.y >= 0) && (coordinate.x < cols) && (coordinate.y < rows)
    }
}
