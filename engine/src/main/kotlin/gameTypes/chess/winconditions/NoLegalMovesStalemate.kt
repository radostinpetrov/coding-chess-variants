package gameTypes.chess.winconditions

import Outcome
import gameMoves.GameMove2D
import gameTypes.chess.AbstractChess
import players.Player

class NoLegalMovesStalemate : WinCondition<AbstractChess> {
    override fun evaluate(game: AbstractChess, player: Player, moves: List<GameMove2D>): Outcome? {
        if (moves.isEmpty()) {
            return Outcome.Draw("Stalemate by No Legal Moves")
        }
        return null
    }
}
