package endconditions

import moves.Move2D
import gameTypes.chess.AbstractChess2D
import players.Player

/**
 * End conditions in standard chess, which consists of:
 * Checkmate,
 * Stalemate by no legal moves,
 * Stalemate by three-fold-repetition,
 * Stalemate by the 50-move rules,
 * Stalemate by insufficient material.
 */
class StandardEndConditions : EndCondition2D<AbstractChess2D> {
    private val endConditions: List<EndCondition2D<AbstractChess2D>> = listOf(Checkmate(), NoLegalMovesStalemate(), ThreeFoldRepetitionStalemate(), InsufficientMaterialStalemate(), FiftyMoveRuleStalemate())
    override fun evaluate(game: AbstractChess2D, player: Player, moves: List<Move2D>): Outcome? {
        for (wc in endConditions) {
            val outcome = wc.evaluate(game, player, moves)
            if (outcome != null) {
                return outcome
            }
        }
        return null
    }
}