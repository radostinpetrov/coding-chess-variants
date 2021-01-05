package gameTypes.chess.winconditions

import Outcome
import gameMoves.GameMove2D
import gameTypes.chess.AbstractChess
import players.Player

class Checkmate : WinCondition2D<AbstractChess> {
    override fun evaluate(game: AbstractChess, player: Player, moves: List<GameMove2D>): Outcome? {
        if (moves.isEmpty() && game.inCheck(player)) {
            val players = game.players
            val winners = players[(players.indexOf(player) + 1) % players.size]
            return Outcome.Win(winners, "by Checkmate")
        }
        return null
    }
}