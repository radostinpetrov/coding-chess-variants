package endconditions.hex

import endconditions.Outcome
import moves.MoveHex
import gameTypes.hex.AbstractChessHex
import players.Player

/**
 * End conditions in standard chess, which consists of:
 * Checkmate,
 * Stalemate by no legal moves,
 * Stalemate by three-fold-repetition,
 * Stalemate by the 50-move rules,
 * Stalemate by insufficient material.
 */
class StandardEndConditionsHex : EndConditionHex<AbstractChessHex> {
    private val endConditions: List<EndConditionHex<AbstractChessHex>> = listOf(Checkmate())
    override fun evaluate(game: AbstractChessHex, player: Player, moves: List<MoveHex>): Outcome? {
        for (wc in endConditions) {
            val outcome = wc.evaluate(game, player, moves)
            if (outcome != null) {
                return outcome
            }
        }
        return null
    }
}