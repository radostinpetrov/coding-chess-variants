package gameTypes.chess.rules

import gameMoves.GameMove2D
import gameTypes.chess.AbstractChess
import players.Player

class ForcedCaptureRule : SpecialRules<AbstractChess> {
    override fun getPossibleMoves(game: AbstractChess, player: Player, moves: MutableList<GameMove2D>) {
        val pred = { it: GameMove2D -> it is GameMove2D.BasicGameMove && it.pieceCaptured != null }
        if (moves.any(pred)) {
            moves.retainAll(pred)
        }
    }
}
