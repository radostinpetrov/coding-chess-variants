package boards

import coordinates.Coordinate2D
import moveGenerators.MoveGeneratorHex
import regions.Region2D
import pieces.hex.PieceHex
import players.Player
import kotlin.math.absoluteValue

/**
 * Implementation of the Board interface for a 2d square board.
 * The board size is rows x cols and each coordinate can have upto one PieceHex.
 *
 * @property verticalLength the number of rows (i.e. number of max width rows)
 * @property maximumWidth the number of columns (i.e. the width)
 * @property outOfBoundsRegion the region that is considered as out of bounds
 */
class BoardHex(val verticalLength: Int, val maximumWidth: Int,
               private val outOfBoundsRegion: Region2D? = null)
    : Board<BoardHex, MoveGeneratorHex, PieceHex, Coordinate2D> {

    init {
        assert(maximumWidth % 2 == 1)
    }

    val rows = verticalLength * 2 - 1 + maximumWidth - 1
    val cols = maximumWidth
    private var board: Array<Array<PieceHex?>> = Array(rows) { Array(cols) { null } }
    override fun getBoardState(): Map<Coordinate2D, PieceHex?> {
        val res = mutableMapOf<Coordinate2D, PieceHex?>()
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

    override fun getPieces(): List<Pair<PieceHex, Coordinate2D>> {
        val res = mutableListOf<Pair<PieceHex, Coordinate2D>>()
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

    override fun getPieces(player: Player): List<Pair<PieceHex, Coordinate2D>> {
        return getPieces().filter { p -> p.first.player === player }
    }

    override fun getPiece(coordinate: Coordinate2D): PieceHex? {
        if (!isInBounds(coordinate)) {
            return null
        }
        return board[coordinate.y][coordinate.x]
    }

    override fun getPieceCoordinate(piece: PieceHex): Coordinate2D? {
        for (y in 0 until rows) {
            for (x in 0 until cols) {
                if (piece === board[y][x]) {
                    return Coordinate2D(x, y)
                }
            }
        }
        return null
    }

    /**
     * @throws Exception if a given coordinate is invalid (not in bound)
     */
    override fun addPiece(coordinate: Coordinate2D, piece: PieceHex) {
        if (!isInBounds(coordinate)) {
            throw Exception("Coordinate not in bound")
        }
        board[coordinate.y][coordinate.x] = piece
    }

    /**
     * @throws ArrayIndexOutOfBoundsException if a given coordinate is invalid
     * @throws Exception if a given piece not on the board
     */
    override fun removePiece(coordinate: Coordinate2D, piece: PieceHex) {
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
        val x = coordinate.x
        val y = coordinate.y
        if (x < 0 || y < 0 || x >= cols || y >= rows) {
            return false
        }

        val deadHeight = maximumWidth / 2

        if (x + y < deadHeight
            || (cols - x) + y < deadHeight
            || x + (rows - y) < deadHeight
            || (cols - x) + (rows - y) <= deadHeight) {
            return false
        }

        if ((x - deadHeight).absoluteValue % 2 == 0) {
            if (y % 2 == 1) {
                return false
            }
        } else {
            if (y % 2 == 0) {
                return false
            }
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
