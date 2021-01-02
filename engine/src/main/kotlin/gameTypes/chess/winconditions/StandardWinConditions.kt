package gameTypes.chess.winconditions

import Outcome
import gameMoves.GameMove2D
import gameTypes.chess.AbstractChess
import players.Player

class StandardWinConditions : WinCondition<AbstractChess> {
    private val winConditions: List<WinCondition<AbstractChess>> = listOf(Checkmate(), ThreeFoldRepetitionStalemate(), NoLegalMovesStalemate(), InsufficientMaterialStalemate())
    override fun evaluate(game: AbstractChess, player: Player, moves: List<GameMove2D>): Outcome? {
        for (wc in winConditions) {
            val outcome = wc.evaluate(game, player, moves)
            if (outcome != null) {
                return outcome
            }
        }
        return null
    }
}