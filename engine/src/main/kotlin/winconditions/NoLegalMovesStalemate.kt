package winconditions

import Outcome
import moves.Move2D
import gameTypes.chess.AbstractChess
import players.Player

class NoLegalMovesStalemate : WinCondition2D<AbstractChess> {
    override fun evaluate(game: AbstractChess, player: Player, moves: List<Move2D>): Outcome? {
        if (moves.isEmpty()) {
            return Outcome.Draw("by No Legal Moves")
        }
        return null
    }
}
