package moves

import boards.Board
import coordinates.Coordinate
import moveGenerators.MoveGenerator
import pieces.Piece
import players.Player

/**
 * Implementation of the Move interface for a 2d square board.
 *
 * @property player the player that makes the move
 * @property displayFrom the start coordinate
 * @property displayTo the end coordinate
 * @property displayPiecePromotedTo the piece it is promoted to (optional)
 * @property displayPieceCaptured the piece it is captured (optional)
 * @property displayPieceMoved the piece to be moved
 */

// ONLY USES PIECE AND COORDINATE -> ROOM FOR REDUCING GENERICS?

sealed class Move<B : Board<B, MG, P, C>,
        MG : MoveGenerator<B, MG, P, C>,
        
        P : Piece<B, MG, P, C>,
        C : Coordinate>
    (open val player: Player) {
    
    abstract val displayFrom: C?
    abstract var displayTo: C?
    abstract var displayPiecePromotedTo: P?
    abstract var displayPieceCaptured: P?
    abstract var displayPieceMoved: P

    sealed class SimpleMove<B : Board<B, MG, P, C>,
            MG : MoveGenerator<B, MG, P, C>,
            
            P : Piece<B, MG, P, C>,
            C : Coordinate>
        (override val player: Player): Move<B, MG, P, C>(player) {
        open val checkForCheck: Boolean = true

        /**
         * Basic Move
         */
        data class BasicMove<B : Board<B, MG, P, C>,
                MG : MoveGenerator<B, MG, P, C>,
                
                P : Piece<B, MG, P, C>,
                C : Coordinate>
            (val from: C, val to: C, val pieceMoved: P, override val player: Player, val pieceCaptured: P? = null, val pieceCapturedCoordinate: C = to, val piecePromotedTo: P? = null, override val checkForCheck: Boolean = true)
            : SimpleMove<B, MG, P, C>(player) {
            override val displayFrom: C
                get() = from
            override var displayTo: C? = to
            override var displayPiecePromotedTo = piecePromotedTo
            override var displayPieceCaptured = pieceCaptured
            override var displayPieceMoved = pieceMoved
        }

        /**
         * A game move to add pieces on the given coordinate
         *
         * @param piece the piece to be added
         * @param coordinate the coordinate for the piece to be added to
         */
        data class AddPieceMove<B : Board<B, MG, P, C>,
                MG : MoveGenerator<B, MG, P, C>,
                
                P : Piece<B, MG, P, C>,
                C : Coordinate>
            (override val player: Player, val piece: P, val coordinate: C): SimpleMove<B, MG, P, C>(player) {
            override val displayFrom = coordinate
            override var displayTo: C? = coordinate
            override var displayPiecePromotedTo: P? = null
            override var displayPieceCaptured: P? = null
            override var displayPieceMoved = piece
        }

        /**
         * A game move to remove pieces on the given coordinate
         *
         * @param piece the piece to be removed
         * @param coordinate the coordinate for the piece to be removed from
         */
        data class RemovePieceMove<B : Board<B, MG, P, C>,
                MG : MoveGenerator<B, MG, P, C>,
                
                P : Piece<B, MG, P, C>,
                C : Coordinate>(override val player: Player, val piece: P, val coordinate: C): SimpleMove<B, MG, P, C>(player) {
            // TODO wtf to do here?
            override val displayFrom: C? = null
            override var displayTo: C? = null
            override var displayPiecePromotedTo: P? = null
            override var displayPieceCaptured: P? = null
            override var displayPieceMoved = piece
        }
    }

    /**
     * A wrapper around a list of simple moves to represent composite moves
     */
    data class CompositeMove<B : Board<B, MG, P, C>,
            MG : MoveGenerator<B, MG, P, C>,
            
            P : Piece<B, MG, P, C>,
            C : Coordinate>(val moves: List<SimpleMove<B, MG, P, C>>, override val player: Player) : Move<B, MG, P, C>(player) {
        override var displayFrom: C? = null
        override var displayTo: C? = null
        override var displayPiecePromotedTo: P? = null
        override var displayPieceCaptured: P? = null
        override lateinit var displayPieceMoved: P

        init {
            val moves = moves.filterIsInstance<SimpleMove.BasicMove<B, MG, P, C>>()
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
