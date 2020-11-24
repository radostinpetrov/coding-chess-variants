import pieces.Piece
import players.Player

sealed class GameMove(open val player: Player) {
    abstract val displayFrom: Coordinate
    abstract var displayTo: Coordinate
    abstract var displayPiecePromotedTo: Piece?

    data class BasicGameMove(val from: Coordinate, val to: Coordinate, val pieceMoved: Piece, override val player: Player, val pieceCaptured: Piece? = null, val piecePromotedTo: Piece? = null, val checkForCheck: Boolean = true) : GameMove(player) {
        override val displayFrom: Coordinate
            get() = from
        override var displayTo: Coordinate = to
        override var displayPiecePromotedTo = piecePromotedTo
    }

    data class CompositeGameMove(val gameMoves: List<BasicGameMove>, override val player: Player) : GameMove(player) {
        override val displayFrom: Coordinate
            get() = gameMoves[0].from
        override var displayTo: Coordinate = Coordinate(0, 0)
        override var displayPiecePromotedTo = gameMoves.last().piecePromotedTo

        init {
            val piece = gameMoves[0].pieceMoved
            for (move in gameMoves.reversed()) {
                if (move.pieceMoved == piece) {
                    displayTo = move.to
                    break
                }
            }
        }
    }
}
