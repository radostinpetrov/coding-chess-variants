package rules

import moves.Move
import moves.Move.SimpleMove.BasicMove
import gameTypes.chess.AbstractChess
import players.Player

/**
 * Forced capture rule used in checker and anti chess.
 * Makes capturing compulsory.
 */
class ForcedCaptureRule : SpecialRules2D<AbstractChess> {
    override fun getPossibleMoves(game: AbstractChess, player: Player, moves: MutableList<Move>) {
        val pred = { it: Move -> it is BasicMove && it.pieceCaptured != null }
        if (moves.any(pred)) {
            moves.retainAll(pred)
        }
    }
}
