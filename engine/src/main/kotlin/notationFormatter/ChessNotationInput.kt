package notationFormatter

import coordinates.Coordinate2D
import gameMoves.GameMove2D
import gameMoves.GameMove2D.CompositeGameMove
import gameMoves.GameMove2D.SimpleGameMove.*

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
    override fun gameMoveToStr(gameMove: GameMove2D): String {
        val sb = StringBuilder()

        when (gameMove) {
            is BasicGameMove -> {
                sb.append(basicGameMoveToStr(gameMove))
            }
            is AddPieceGameMove -> {
                sb.append("${gameMove.piece.getSymbol()} is placed on the board at ${coordinateToStr(gameMove.coordinate)}")
            }
            is RemovePieceGameMove -> {
                sb.append("${gameMove.piece.getSymbol()} is removed from the board at ${coordinateToStr(gameMove.coordinate)}")
            }
            is CompositeGameMove -> {
                val gameMoves = gameMove.gameMoves
                for ((i, move) in gameMoves.withIndex()) {
                    sb.append(gameMoveToStr(move))
                    if (i != gameMoves.size - 1) {
                        sb.append(", ")
                    }
                }
                sb.trimEnd()
            }
        }

        return sb.toString()
    }

    private fun basicGameMoveToStr(gameMove: BasicGameMove): String {
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
