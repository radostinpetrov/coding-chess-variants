package notationFormatter

import Coordinate
import GameMove

class ChessNotationInput(val height: Int) : NotationFormatter {

    /* Converts the string representation of a coordinate to a coordinate. e.g A1 -> Coordinate(0, 6) */
    override fun strToCoordinate(s: String): Coordinate? {
        /* Check that the input string matches the pattern for standard chess notation. */
        val regex = """([a-z]+)(\d+)""".toRegex()
        val matchResult = regex.matchEntire(s) ?: return null
        val (l, n) = matchResult.destructured

        val c1 = l[0].toInt() - 'a'.toInt()
        val c2 = height - n.toInt()
        return Coordinate(c1, c2)
    }

    /* Converts a coordinate to the string representation of a coordinate. e.g Coordinate(0, 6) -> A1
    * TODO: aa -> 27 etc.*/
    override fun coordinateToStr(c: Coordinate): String? {
        val c1 = (c.x + 'a'.toInt()).toChar()
        val c2 = height - c.y
        return "$c1$c2"
    }

    /* Get the string representation of a game move. */
    override fun gameMoveToStr(gameMove: GameMove): String {
        val sb = StringBuilder()

        when (gameMove) {
            is GameMove.BasicGameMove -> {
                sb.append(basicGameMoveToStr(gameMove))
            }
            is GameMove.CompositeGameMove -> {
                for (move in gameMove.gameMoves) {
                    sb.append(gameMoveToStr(move))
                    sb.append(' ')
                }
                sb.trimEnd()
            }
        }

        return sb.toString()
    }

    private fun basicGameMoveToStr(gameMove: GameMove.BasicGameMove): String {
        val sb = StringBuilder()
        sb.append("${gameMove.pieceMoved.getSymbol()} moves from ${coordinateToStr(gameMove.from)} to ${coordinateToStr(gameMove.to)}")

        if (gameMove.pieceCaptured != null) {
            sb.append(", capturing ${gameMove.pieceCaptured.getSymbol()}")
        }

        if (gameMove.piecePromotedTo != null) {
            sb.append(" and promoting to ${gameMove.piecePromotedTo.getSymbol()}")
        }
        return sb.toString()
    }
}
