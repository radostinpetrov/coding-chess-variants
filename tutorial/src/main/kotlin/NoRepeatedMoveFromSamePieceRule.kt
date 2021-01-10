import gameTypes.chess.AbstractChess2D
import moves.Move2D
import players.Player
import rules.SpecialRules2D

class NoRepeatedMoveFromSamePieceRule() : SpecialRules2D<AbstractChess2D> {
    override fun getPossibleMoves(game: AbstractChess2D, player: Player, moves: MutableList<Move2D>) {
        if (game.moveLog.size < 2) {
            return
        }
        val prevMove = game.moveLog[game.moveLog.size - 2]
        moves.removeAll { it.displayPieceMoved === prevMove.displayPieceMoved }
    }
}
