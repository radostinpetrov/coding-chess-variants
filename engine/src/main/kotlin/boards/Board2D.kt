package boards

import coordinates.Coordinate2D
import moves.Move2D
import moveGenerators.MoveGenerator2D
import regions.Region2D
import pieces.Piece2D
import players.Player

/**
 * Implementation of the Board interface for a 2d square board.
 * The board size is rows x cols and each coordinate can have upto one Piece2D.
 *
 * @property rows the number of rows
 * @property cols the number of columns
 * @property outOfBoundsRegion the region that is considered as out of bounds
 */
class Board2D(val rows: Int, val cols: Int,
              private val outOfBoundsRegion: Region2D? = null)
    : Board<Board2D, MoveGenerator2D, Move2D, Piece2D, Coordinate2D> {
    private var board: Array<Array<Piece2D?>> = Array(rows) { Array(cols) { null } }
    override fun getBoardState(): Map<Coordinate2D, Piece2D?> {
        val res = mutableMapOf<Coordinate2D, Piece2D?>()
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val coordinate = Coordinate2D(col, row)
                if (isInBounds(coordinate)) {
                    res[coordinate] = board[row][col]
                }
            }
        }

        return res.toMap()
    }

    override fun getPieces(): List<Pair<Piece2D, Coordinate2D>> {
        val res = mutableListOf<Pair<Piece2D, Coordinate2D>>()
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                if (!isInBounds(Coordinate2D(j, i))) {
                    continue
                }
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
            throw Exception("Coordinate not in bound")
        }
        board[coordinate.y][coordinate.x] = piece
    }

    override fun removePiece(coordinate: Coordinate2D, piece: Piece2D) {
        if (!isInBounds(coordinate)) {
            throw ArrayIndexOutOfBoundsException()
        }
        if (board[coordinate.y][coordinate.x] != piece) {
            print(piece)
            throw Exception("Cannot remove non-existing piece")
        }
        board[coordinate.y][coordinate.x] = null
    }

    /**
     * Returns true if the given coordinate is valid on the board
     */
    fun isInBounds(coordinate: Coordinate2D): Boolean {
        if (coordinate.x < 0 || coordinate.y < 0 || coordinate.x >= cols || coordinate.y >= rows) {
            return false
        }
        return outOfBoundsRegion == null || !outOfBoundsRegion.isInRegion(coordinate)
    }

    override fun clearBoard() {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                board[row][col] = null
            }
        }
    }
}
