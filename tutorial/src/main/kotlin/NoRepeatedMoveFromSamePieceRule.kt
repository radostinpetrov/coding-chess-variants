import gameTypes.chess.AbstractChess
import moves.Move
import players.Player
import rules.SpecialRules2D

class NoRepeatedMoveFromSamePieceRule() : SpecialRules2D<AbstractChess> {
    override fun getPossibleMoves(game: AbstractChess, player: Player, moves: MutableList<Move>) {
        if (game.moveLog.size < 2) {
            return
        }
        val prevMove = game.moveLog[game.moveLog.size - 2]
        moves.removeAll { it.displayPieceMoved === prevMove.displayPieceMoved }
    }
}
