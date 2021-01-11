package endconditions

import gameTypes.chess3d.AbstractChess3D
import moves.Move3D
import players.Player

/**
 * Condition for checkmate.
 * If the player on turn has no legal move but is not in check
 */
class EndConditions3DChess : EndCondition3D<AbstractChess3D> {
    override fun evaluate(game: AbstractChess3D, player: Player, moves: List<Move3D>): Outcome? {
        if (moves.isEmpty()) {
            return if (game.inCheck(player)) {
                val players = game.players
                val winners = players[(players.indexOf(player) + 1) % players.size]
                Outcome.Win(winners, "by Checkmate")
            } else {
                Outcome.Draw("by No Legal Moves")
            }
        }
        return null
    }
}