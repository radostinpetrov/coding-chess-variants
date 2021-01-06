package rules

import moves.Move2D
import moves.Move2D.SimpleMove.BasicMove
import gameTypes.chess.AbstractChess
import players.Player

class ForcedCaptureRule : SpecialRules2D<AbstractChess> {
    override fun getPossibleMoves(game: AbstractChess, player: Player, moves: MutableList<Move2D>) {
        val pred = { it: Move2D -> it is BasicMove && it.pieceCaptured != null }
        if (moves.any(pred)) {
            moves.retainAll(pred)
        }
    }
}
