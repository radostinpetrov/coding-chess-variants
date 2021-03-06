import endconditions.Outcome
import gameTypes.chess.AbstractChess2D
import moves.Move2D
import pieces.Pawn
import players.Player
import endconditions.EndCondition2D

class TutorialEndCondition : EndCondition2D<AbstractChess2D> {
    override fun evaluate(game: AbstractChess2D, player: Player, moves: List<Move2D>): Outcome? {
        for (p in game.players) {
            if (!game.board.getPieces(p).any { piece -> piece.first is Pawn }) {
                return Outcome.Win(game.getOpponentPlayer(p), "by opponent losing all pawns")
            }
        }
        return null
    }
}
