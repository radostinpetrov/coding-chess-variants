package utils.notationFormatter

import coordinates.Coordinate2D
import moves.*

/**
 * ChessNotationInput
 * Converts standard ches notation to Coordinate2D and Coordinate2D,
 * MoveHex to readable string format. Is used for console front end.
 */
class ChessNotationInputHex() {

    /**
     * Takes a String representation of a coordinate and converts to a Coordinate2D.
     * @return Coordinate2D
     */
    fun strToCoordinate(s: String): Coordinate2D? {
        /* Check that the input string matches the pattern for standard chess notation. */
        val regex = """([a-z]+)(\d+)""".toRegex()
        val matchResult = regex.matchEntire(s) ?: return null
        val (l, n) = matchResult.destructured

        val c1 = l[0].toInt() - 'a'.toInt()
        val c2 = n.toInt() - 1
        return Coordinate2D(c1, c2)
    }

    /**
     * Takes a Coordinate2D and converts it into standard chess notation. e.g Coordinate(0, 6) -> A1
     * @returns string coordinate.
     */
    fun coordinateToStr(c: Coordinate2D): String {
        val c1 = (c.x + 'a'.toInt()).toChar()
        val c2 = c.y + 1
        return "$c1$c2"
    }

    /**
     * Takes a MoveHex and converts it to a readable chess notation.
     * @return the string representing the move.
     */
    fun moveToStr(move: MoveHex): String {
        val sb = StringBuilder()

        when (move) {
            is BasicMoveHex -> {
                sb.append(basicMoveToStr(move))
            }
            is AddPieceMoveHex -> {
                sb.append("${move.piece.getSymbol()} is placed on the board at ${coordinateToStr(move.coordinate)}")
            }
            is RemovePieceMoveHex -> {
                sb.append("${move.piece.getSymbol()} is removed from the board at ${coordinateToStr(move.coordinate)}")
            }
            is CompositeMoveHex -> {
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
    private fun basicMoveToStr(move: BasicMoveHex): String {
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
