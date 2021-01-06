package moves

import boards.Board2D
import coordinates.Coordinate2D
import moveGenerators.MoveGenerator2D
import pieces.Piece2D
import players.Player

sealed class Move2D(open val player: Player) : Move<Board2D, MoveGenerator2D, Move2D, Piece2D, Coordinate2D> {
    abstract val displayFrom: Coordinate2D
    abstract var displayTo: Coordinate2D
    abstract var displayPiecePromotedTo: Piece2D?
    abstract var displayPieceCaptured: Piece2D?
    abstract var displayPieceMoved: Piece2D

    sealed class SimpleMove(override val player: Player): Move2D(player) {
        open val checkForCheck: Boolean = true

        data class BasicMove(val from: Coordinate2D, val to: Coordinate2D, val pieceMoved: Piece2D, override val player: Player, val pieceCaptured: Piece2D? = null, val piecePromotedTo: Piece2D? = null, override val checkForCheck: Boolean = true)
            : SimpleMove(player) {
            override val displayFrom: Coordinate2D
                get() = from
            override var displayTo: Coordinate2D = to
            override var displayPiecePromotedTo = piecePromotedTo
            override var displayPieceCaptured = pieceCaptured
            override var displayPieceMoved = pieceMoved
        }

        data class AddPieceMove(override val player: Player, val piece: Piece2D, val coordinate: Coordinate2D): SimpleMove(player) {
            override val displayFrom = coordinate
            override var displayTo = coordinate
            override var displayPiecePromotedTo: Piece2D? = null
            override var displayPieceCaptured: Piece2D? = null
            override var displayPieceMoved = piece
        }

        data class RemovePieceMove(override val player: Player, val piece: Piece2D, val coordinate: Coordinate2D): SimpleMove(player) {
            override val displayFrom = Coordinate2D(-1, -1)
            override var displayTo = Coordinate2D(-1, -1)
            override var displayPiecePromotedTo: Piece2D? = null
            override var displayPieceCaptured: Piece2D? = null
            override var displayPieceMoved = piece
        }
    }

    data class CompositeMove(val moves: List<SimpleMove>, override val player: Player) : Move2D(player) {
        override var displayFrom: Coordinate2D = Coordinate2D(0, 0)
        override var displayTo: Coordinate2D = Coordinate2D(0, 0)
        override var displayPiecePromotedTo: Piece2D? = null
        override var displayPieceCaptured: Piece2D? = null
        override lateinit var displayPieceMoved: Piece2D

        init {
            val moves = moves.filterIsInstance<SimpleMove.BasicMove>()
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
                displayPieceMoved = moves.first().pieceMoved
            }
        }
    }
}
