package endconditions

import moves.Move2D
import gameTypes.chess.AbstractChess2D
import pieces.Pawn
import players.Player

/**
 * Condition for stalemate by the 50-move rules.
 *
 * In the previous 50 moves by each side,
 * no pawn has moved and no capture has been made.
 */
class FiftyMoveRuleStalemate : EndCondition2D<AbstractChess2D> {
    override fun evaluate(game: AbstractChess2D, player: Player, moves: List<Move2D>): Outcome? {
        if (moves.size < 100) {
            return null
        }

        if (moves.any { m -> m.displayPieceMoved is Pawn || m.displayPieceCaptured != null }) {
            return null
        }

        return Outcome.Draw("by 50-move rule")
    }
}