package gameMoves

import boards.Board2D
import coordinates.Coordinate2D
import moves.Move2D
import pieces.Piece2D
import players.Player

sealed class GameMove2D(open val player: Player) : GameMove<Board2D, Move2D, GameMove2D.BasicGameMove, Piece2D, Coordinate2D>  {
    abstract val displayFrom: Coordinate2D
    abstract var displayTo: Coordinate2D
    abstract var displayPiecePromotedTo: Piece2D?

    data class BasicGameMove(val from: Coordinate2D, val to: Coordinate2D, val pieceMoved: Piece2D, override val player: Player, val pieceCaptured: Piece2D? = null, val piecePromotedTo: Piece2D? = null, val checkForCheck: Boolean = true) : GameMove2D(player) {
        override val displayFrom: Coordinate2D
            get() = from
        override var displayTo: Coordinate2D = to
        override var displayPiecePromotedTo = piecePromotedTo
    }

    data class CompositeGameMove(val gameMoves: List<BasicGameMove>, override val player: Player) : GameMove2D(player) {
        override val displayFrom: Coordinate2D
            get() = gameMoves[0].from
        override var displayTo: Coordinate2D = Coordinate2D(0, 0)
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
