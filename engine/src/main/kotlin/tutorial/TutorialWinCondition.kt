package tutorial

import winconditions.WinCondition2D
import Outcome
import moves.Move2D
import gameTypes.chess.AbstractChess
import players.Player

class TutorialWinCondition : WinCondition2D<TutorialChess> {
    override fun evaluate(game: AbstractChess, player: Player, moves: List<Move2D>): Outcome? {
        if (moves.isEmpty()) {
            return Outcome.Draw("by No Legal Moves")
        }
        return null
    }
}
