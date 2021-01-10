package endconditions

import moves.Move2D
import gameTypes.chess.AbstractChess2D
import players.Player

/**
 * Condition for stalemate by no legal moves
 */
class NoLegalMovesStalemate : EndCondition2D<AbstractChess2D> {
    override fun evaluate(game: AbstractChess2D, player: Player, moves: List<Move2D>): Outcome? {
        if (moves.isEmpty()) {
            return Outcome.Draw("by No Legal Moves")
        }
        return null
    }
}
