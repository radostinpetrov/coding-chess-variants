import pieces.Piece

data class GameMove(val from: Coordinate, val to: Coordinate, val pieceMoved: Piece, val pieceCaptured: Piece? = null, val piecePromotedTo: Piece? = null) {
    override fun toString(): String {
        val sb = StringBuilder()

        sb.append("${pieceMoved.getSymbol()} moves from $from to $to")

        if (pieceCaptured != null) {
            sb.append(", capturing ${pieceCaptured.getSymbol()}")
        }

        if (piecePromotedTo != null) {
            sb.append(" and promoting to ${piecePromotedTo.getSymbol()}")
        }

        return sb.toString()
    }
}
