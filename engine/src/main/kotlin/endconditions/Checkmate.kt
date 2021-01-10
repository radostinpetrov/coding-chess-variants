package endconditions

import moves.Move2D
import gameTypes.chess.AbstractChess2D
import players.Player

/**
 * Condition for checkmate.
 * If the player on turn has no legal move but is not in check
 */
class Checkmate : EndCondition2D<AbstractChess2D> {
    override fun evaluate(game: AbstractChess2D, player: Player, moves: List<Move2D>): Outcome? {
        if (moves.isEmpty() && game.inCheck(player)) {
            val players = game.players
            val winners = players[(players.indexOf(player) + 1) % players.size]
            return Outcome.Win(winners, "by Checkmate")
        }
        return null
    }
}