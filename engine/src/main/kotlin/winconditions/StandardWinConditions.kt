package winconditions

import Outcome
import moves.Move2D
import gameTypes.chess.AbstractChess
import players.Player

/**
 * Win conditions in standard chess
 *
 * @property winConditions consists of:
 * Checkmate
 * Stalemate by no legal moves
 * Stalemate by three-fold-repetition
 * Stalemate by the 50-move rules
 * Stalemate by insufficient material.
 */
class StandardWinConditions : WinCondition2D<AbstractChess> {
    private val winConditions: List<WinCondition2D<AbstractChess>> = listOf(Checkmate(), NoLegalMovesStalemate(), ThreeFoldRepetitionStalemate(), InsufficientMaterialStalemate(), FiftyMoveRuleStalemate())
    override fun evaluate(game: AbstractChess, player: Player, moves: List<Move2D>): Outcome? {
        for (wc in winConditions) {
            val outcome = wc.evaluate(game, player, moves)
            if (outcome != null) {
                return outcome
            }
        }
        return null
    }
}