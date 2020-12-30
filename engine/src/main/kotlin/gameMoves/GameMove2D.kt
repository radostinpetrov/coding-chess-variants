package gameMoves

import boards.Board2D
import coordinates.Coordinate2D
import moves.Move2D
import pieces.Piece2D
import players.Player

sealed class GameMove2D(open val player: Player) : GameMove<Board2D, Move2D, GameMove2D.SimpleGameMove.BasicGameMove, Piece2D, Coordinate2D>  {
    abstract val displayFrom: Coordinate2D
    abstract var displayTo: Coordinate2D
    abstract var displayPiecePromotedTo: Piece2D?
    abstract var displayPieceCaptured: Piece2D?

    sealed class SimpleGameMove(override val player: Player): GameMove2D(player) {
        open val checkForCheck: Boolean = true

        data class BasicGameMove(val from: Coordinate2D, val to: Coordinate2D, val pieceMoved: Piece2D, override val player: Player, val pieceCaptured: Piece2D? = null, val piecePromotedTo: Piece2D? = null, override val checkForCheck: Boolean = true)
            : SimpleGameMove(player) {
            override val displayFrom: Coordinate2D
                get() = from
            override var displayTo: Coordinate2D = to
            override var displayPiecePromotedTo = piecePromotedTo
            override var displayPieceCaptured = pieceCaptured
        }

        /* make it into something else later :  ) */
        data class AddPiece(override val player: Player, val piece: Piece2D, val coordinate: Coordinate2D): SimpleGameMove(player) {
            override val displayFrom = coordinate
            override var displayTo = coordinate
            override var displayPiecePromotedTo: Piece2D? = null
            override var displayPieceCaptured: Piece2D? = null
        }

        data class RemovePiece(override val player: Player, val piece: Piece2D, val coordinate: Coordinate2D): SimpleGameMove(player) {
            override val displayFrom = Coordinate2D(-1, -1)
            override var displayTo = Coordinate2D(-1, -1)
            override var displayPiecePromotedTo: Piece2D? = null
            override var displayPieceCaptured: Piece2D? = null
            }
    }

    data class CompositeGameMove(val gameMoves: List<SimpleGameMove>, override val player: Player) : GameMove2D(player) {
        override var displayFrom: Coordinate2D = Coordinate2D(0, 0)
        override var displayTo: Coordinate2D = Coordinate2D(0, 0)
        override var displayPiecePromotedTo: Piece2D? = null
        override var displayPieceCaptured: Piece2D? = null

        init {
            val moves = gameMoves.filterIsInstance<SimpleGameMove.BasicGameMove>()
            if (moves.isNotEmpty()) {
                displayFrom = moves.first().from

                val piece = moves.first().pieceMoved
                for (move in moves.reversed()) {
                    if (move.pieceMoved == piece) {
                        displayTo = move.to
                        break
                    }
                }

                displayPiecePromotedTo = moves.last().piecePromotedTo
                displayPieceCaptured =  moves.lastOrNull { it.pieceCaptured != null }?.pieceCaptured
            }
        }
    }
}
