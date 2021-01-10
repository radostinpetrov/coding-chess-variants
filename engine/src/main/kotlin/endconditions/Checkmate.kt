package endconditions

import moves.Move
import gameTypes.chess.AbstractChess
import players.Player

/**
 * Condition for checkmate.
 * If the player on turn has no legal move but is not in check
 */
class Checkmate : EndCondition2D<AbstractChess> {
    override fun evaluate(game: AbstractChess, player: Player, moves: List<Move>): Outcome? {
        if (moves.isEmpty() && game.inCheck(player)) {
            val players = game.players
            val winners = players[(players.indexOf(player) + 1) % players.size]
            return Outcome.Win(winners, "by Checkmate")
        }
        return null
    }
}