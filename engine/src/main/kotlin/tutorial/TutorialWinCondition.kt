package tutorial

import winconditions.WinCondition2D
import Outcome
import gameTypes.chess.AbstractChess
import moves.Move2D
import pieces.Pawn
import players.Player

class TutorialWinCondition : WinCondition2D<AbstractChess> {
    override fun evaluate(game: AbstractChess, player: Player, moves: List<Move2D>): Outcome? {
        for (p in game.players) {
            if (!game.board.getPieces(p).any { piece -> piece is Pawn }) {
                return Outcome.Win(game.getOpponentPlayer(p), "by opponent losing all pawns")
            }
        }

        if (moves.isEmpty()) {
            return Outcome.Draw("by no feasible moves")
        }

        return null
    }
}
