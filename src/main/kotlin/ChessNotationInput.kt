class ChessNotationInput(val height: Int) : NotationFormatter {
    override fun strToCoordinate(s: String): Coordinate? {
        val regex =
            """([a-z]+)(\d+)""".toRegex()
        val matchResult = regex.matchEntire(s) ?: return null
        val (l, n) = matchResult.destructured
        val c1 = l[0].toInt() - 'a'.toInt()
        val c2 = height - n.toInt()
        return Coordinate(c1, c2)
    }

    override fun coordinateToStr(c: Coordinate): String? {
        val c1 = (c.x + 'a'.toInt()).toChar()
        val c2 = height - c.y
        return "${c1}$c2"
    }

    override fun gameMoveToStr(gameMove: GameMove): String {
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
