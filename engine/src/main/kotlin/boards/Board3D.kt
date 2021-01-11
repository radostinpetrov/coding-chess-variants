package boards

import coordinates.Coordinate3D
import moveGenerators.MoveGenerator3D
import pieces.Piece3D
import players.Player
import regions.Region3D

/**
 * Implementation of the Board interface for a 3D square board.
 * The board size is rows x cols and each coordinate can have upto one Piece3D.
 *
 * @property dims the dimension
 * @property rows the number of rows
 * @property cols the number of columns
 * @property outOfBoundsRegion the region that is considered as out of bounds
 */
class Board3D(val dims: Int, val rows: Int, val cols: Int, private val outOfBoundsRegion: Region3D? = null) :
    Board<Board3D, MoveGenerator3D, Piece3D, Coordinate3D> {
    private var board: Array<Array<Array<Piece3D?>>> = Array(dims) { Array(rows) { Array(cols) { null } } }
    override fun getBoardState(): Map<Coordinate3D, Piece3D?> {
        val res = mutableMapOf<Coordinate3D, Piece3D?>()
        for (dim in 0 until dims) {
            for (row in 0 until rows) {
                for (col in 0 until cols) {
                    val coordinate = Coordinate3D(col, row, dim)
                    if (isInBounds(coordinate)) {
                        res[coordinate] = board[dim][row][col]
                    }
                }
            }
        }

        return res.toMap()
    }

    override fun getPieces(): List<Pair<Piece3D, Coordinate3D>> {
        val res = mutableListOf<Pair<Piece3D, Coordinate3D>>()
        for (i in 0 until dims) {
            for (j in 0 until rows) {
                for (k in 0 until cols) {
                    val c = Coordinate3D(k, j, i)
                    if (!isInBounds(c)) {
                        continue
                    }
                    val p = getPiece(c)
                    if (p != null) {
                        res.add(Pair(p, c))
                    }
                }
            }
        }
        return res
    }

    override fun getPieces(player: Player): List<Pair<Piece3D, Coordinate3D>> {
        return getPieces().filter { p -> p.first.player === player }
    }

    override fun getPiece(coordinate: Coordinate3D): Piece3D? {
        if (!isInBounds(coordinate)) {
            return null
        }
        return board[coordinate.z][coordinate.y][coordinate.x]
    }

    override fun getPieceCoordinate(piece: Piece3D): Coordinate3D? {
        for (z in 0 until dims) {
            for (y in 0 until rows) {
                for (x in 0 until cols) {
                    if (piece === board[z][y][x]) {
                        return Coordinate3D(x, y, z)
                    }
                }
            }
        }
        return null
    }

    /**
     * @throws Exception if a given coordinate is invalid (not in bound)
     */
    override fun addPiece(coordinate: Coordinate3D, piece: Piece3D) {
        if (!isInBounds(coordinate)) {
            throw Exception("Coordinate not in bound")
        }
        board[coordinate.z][coordinate.y][coordinate.x] = piece
    }

    /**
     * @throws ArrayIndexOutOfBoundsException if a given coordinate is invalid
     * @throws Exception if a given piece not on the board
     */
    override fun removePiece(coordinate: Coordinate3D, piece: Piece3D) {
        if (!isInBounds(coordinate)) {
            throw ArrayIndexOutOfBoundsException()
        }
        if (board[coordinate.z][coordinate.y][coordinate.x] != piece) {
            print(piece)
            throw Exception("Cannot remove non-existing piece")
        }
        board[coordinate.z][coordinate.y][coordinate.x] = null
    }

    /**
     * Returns true if the given coordinate is valid on the board
     */
    fun isInBounds(coordinate: Coordinate3D): Boolean {
        if (coordinate.x < 0 || coordinate.y < 0 || coordinate.x >= cols || coordinate.y >= rows || coordinate.z < 0 || coordinate.z >= dims) {
            return false
        }
        return outOfBoundsRegion == null || !outOfBoundsRegion.isInRegion(coordinate)
    }

    override fun clearBoard() {
        for (dim in 0 until dims) {
            for (row in 0 until rows) {
                for (col in 0 until cols) {
                    board[dim][row][col] = null
                }
            }
        }
    }
}
