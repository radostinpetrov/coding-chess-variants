package endconditions.hex

import endconditions.Outcome
import moves.MoveHex
import gameTypes.hex.AbstractChessHex
import players.Player

/**
 * Condition for checkmate.
 * If the player on turn has no legal move but is not in check
 */
class Checkmate : EndConditionHex<AbstractChessHex> {
    override fun evaluate(game: AbstractChessHex, player: Player, moves: List<MoveHex>): Outcome? {
        if (moves.isEmpty() && game.inCheck(player)) {
            val players = game.players
            val winners = players[(players.indexOf(player) + 1) % players.size]
            return Outcome.Win(winners, "by Checkmate")
        }
        return null
    }
}