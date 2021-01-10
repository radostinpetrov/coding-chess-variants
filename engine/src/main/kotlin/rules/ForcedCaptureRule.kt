package rules

import moves.*
import gameTypes.chess.AbstractChess
import players.Player

/**
 * Forced capture rule used in checker and anti chess.
 * Makes capturing compulsory.
 */
class ForcedCaptureRule : SpecialRules2D<AbstractChess> {
    override fun getPossibleMoves(game: AbstractChess, player: Player, moves: MutableList<Move2D>) {
        val pred = { it: Move2D -> it is BasicMove2D && it.pieceCaptured != null }
        if (moves.any(pred)) {
            moves.retainAll(pred)
        }
    }
}
