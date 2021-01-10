package rules

import moves.*
import gameTypes.chess.AbstractChess2D
import players.Player

/**
 * Forced capture rule used in checker and anti chess.
 * Makes capturing compulsory.
 */
class ForcedCaptureRule : SpecialRules2D<AbstractChess2D> {
    override fun getPossibleMoves(game: AbstractChess2D, player: Player, moves: MutableList<Move2D>) {
        val pred = { it: Move2D -> it is BasicMove2D && it.pieceCaptured != null }
        if (moves.any(pred)) {
            moves.retainAll(pred)
        }
    }
}
