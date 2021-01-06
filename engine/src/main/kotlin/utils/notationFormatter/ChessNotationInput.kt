package utils.notationFormatter

import coordinates.Coordinate2D
import moves.Move2D
import moves.Move2D.CompositeMove
import moves.Move2D.SimpleMove.*

class ChessNotationInput() : NotationFormatter {

    /* Converts the string representation of a coordinate to a coordinate. e.g A1 -> Coordinate(0, 6) */
    override fun strToCoordinate(s: String): Coordinate2D? {
        /* Check that the input string matches the pattern for standard chess notation. */
        val regex = """([a-z]+)(\d+)""".toRegex()
        val matchResult = regex.matchEntire(s) ?: return null
        val (l, n) = matchResult.destructured

        val c1 = l[0].toInt() - 'a'.toInt()
        val c2 = n.toInt() - 1
        return Coordinate2D(c1, c2)
    }

    /* Converts a coordinate to the string representation of a coordinate. e.g Coordinate(0, 6) -> A1
    * TODO: aa -> 27 etc.*/
    override fun coordinateToStr(c: Coordinate2D): String {
        val c1 = (c.x + 'a'.toInt()).toChar()
        val c2 = c.y + 1
        return "$c1$c2"
    }

    /* Get the string representation of a game move. */
    override fun moveToStr(move: Move2D): String {
        val sb = StringBuilder()

        when (move) {
            is BasicMove -> {
                sb.append(basicMoveToStr(move))
            }
            is AddPieceMove -> {
                sb.append("${move.piece.getSymbol()} is placed on the board at ${coordinateToStr(move.coordinate)}")
            }
            is RemovePieceMove -> {
                sb.append("${move.piece.getSymbol()} is removed from the board at ${coordinateToStr(move.coordinate)}")
            }
            is CompositeMove -> {
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

    private fun basicMoveToStr(move: BasicMove): String {
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
