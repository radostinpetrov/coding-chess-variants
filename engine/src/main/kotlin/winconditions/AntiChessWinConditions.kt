package winconditions

import Outcome
import moves.Move2D
import gameTypes.chess.AbstractChess
import players.Player

class AntiChessWinConditions : WinCondition2D<AbstractChess> {
    override fun evaluate(game: AbstractChess, player: Player, moves: List<Move2D>): Outcome? {
        for (p in game.players) {
            val pieces = game.board.getPieces(p)
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