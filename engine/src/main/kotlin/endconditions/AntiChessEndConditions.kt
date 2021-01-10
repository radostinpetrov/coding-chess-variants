package endconditions

import gameTypes.chess.AbstractChess2D
import moves.Move2D
import players.Player

/**
 * End conditions for anti chess.
 * The objective of each player is to lose all of their pieces or be stalemated.
 */
class AntiChessEndConditions : EndCondition2D<AbstractChess2D> {
    override fun evaluate(game: AbstractChess2D, player: Player, moves: List<Move2D>): Outcome? {
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
