package winconditions

import Outcome
import moves.Move2D
import gameTypes.chess.AbstractChess
import players.Player

class StandardWinConditions : WinCondition2D<AbstractChess> {
    private val winConditions: List<WinCondition2D<AbstractChess>> = listOf(Checkmate(), NoLegalMovesStalemate(), ThreeFoldRepetitionStalemate(), InsufficientMaterialStalemate(), FiftyMoveRuleStalemate())
    override fun evaluate(game: AbstractChess, player: Player, moves: List<Move2D>): Outcome? {
        for (wc in winConditions) {
            val outcome = wc.evaluate(game, player, moves)
            if (outcome != null) {
                return outcome
            }
        }
        return null
    }
}