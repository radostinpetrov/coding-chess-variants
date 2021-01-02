package gameTypes.chess.winconditions

import Outcome
import gameMoves.GameMove2D
import gameTypes.chess.AbstractChess
import players.Player

class AntiChessWinConditions : WinCondition<AbstractChess> {
    override fun evaluate(game: AbstractChess, player: Player, moves: List<GameMove2D>): Outcome? {
        for (p in game.players) {
            val pieces = game.board.getPieces(player)
            if (pieces.isEmpty()) {
                return Outcome.Win(p, "by losing all pieces")
            }
        }
        if (moves.isEmpty()) {
            return Outcome.Win(player, "by stalemated player")
        }
        return null
    }
}