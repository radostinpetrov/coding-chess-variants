package gameTypes.chess.rules

import GameMove
import gameTypes.chess.AbstractChess
import players.Player

class ForcedCaptureRule : SpecialRules<AbstractChess> {
    override fun getPossibleMoves(game: AbstractChess, player: Player, moves: MutableList<GameMove>) {
        val pred = { it: GameMove -> it is GameMove.BasicGameMove && it.pieceCaptured != null }
        if (moves.any(pred)) {
            moves.retainAll(pred)
        }
    }
}
