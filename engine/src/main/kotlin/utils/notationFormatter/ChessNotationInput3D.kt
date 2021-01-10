package utils.notationFormatter

import coordinates.Coordinate3D
import moves.*

/**
 * ChessNotationInput
 * Converts standard ches notation to Coordinate3D and Coordinate3D,
 * Move3D to readable string format. Is used for console front end.
 */
class ChessNotationInput3D() {

    /**
     * Takes a String representation of a coordinate and converts to a Coordinate3D.
     * @return Coordinate3D
     */
    fun strToCoordinate(s: String): Coordinate3D? {
        /* Check that the input string matches the pattern for standard chess notation. */
        val regex = """([A-Z])([a-z])(\d+)""".toRegex()
        val matchResult = regex.matchEntire(s) ?: return null
        val (l1, l2, n) = matchResult.destructured

        val c1 = l1[0].toInt() - 'A'.toInt()
        val c2 = l2[0].toInt() - 'a'.toInt()
        val c3 = n.toInt() - 1
        return Coordinate3D(c2, c3, c1)
    }

    /**
     * Takes a Coordinate3D and converts it into standard chess notation. e.g Coordinate(0, 6) -> A1
     * @returns string coordinate.
     */
    fun coordinateToStr(c: Coordinate3D): String {
        val c1 = (c.z + 'A'.toInt()).toChar()
        val c2 = (c.x + 'a'.toInt()).toChar()
        val c3 = c.y + 1
        return "$c1$c2$c3"
    }

    /**
     * Takes a Move3D and converts it to a readable chess notation.
     * @return the string representing the move.
     */
    fun moveToStr(move: Move3D): String {
        val sb = StringBuilder()

        when (move) {
            is BasicMove3D -> {
                sb.append(basicMoveToStr(move))
            }
            is AddPieceMove3D -> {
                sb.append("${move.piece.getSymbol()} is placed on the board at ${coordinateToStr(move.coordinate)}")
            }
            is RemovePieceMove3D -> {
                sb.append("${move.piece.getSymbol()} is removed from the board at ${coordinateToStr(move.coordinate)}")
            }
            is CompositeMove3D -> {
                val moves = move.moves
                for ((i, m) in moves.withIndex()) {
                    sb.append(moveToStr(m))
                    if (i != moves.size - 1) {
                        sb.append(", ")
                    }
                }
                sb.trimEnd()
            }
        }

        return sb.toString()
    }

    /**
     * Takes a BasicMove and converts it to a readable chess notation.
     * Used as a helper function for moveToStr().
     * @return the string representing the move.
     */
    private fun basicMoveToStr(move: BasicMove3D): String {
        val sb = StringBuilder()
        sb.append("${move.pieceMoved.getSymbol()} moves from ${coordinateToStr(move.from)} to ${coordinateToStr(move.to)}")

        if (move.pieceCaptured != null) {
            sb.append(", capturing ${move.pieceCaptured.getSymbol()}")
        }

        if (move.piecePromotedTo != null) {
            sb.append(" and promoting to ${move.piecePromotedTo.getSymbol()}")
        }
        return sb.toString()
    }
}
