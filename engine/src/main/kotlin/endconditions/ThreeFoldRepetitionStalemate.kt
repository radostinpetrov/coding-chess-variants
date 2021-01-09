package endconditions

import moves.Move2D
import gameTypes.chess.AbstractChess
import players.Player

/**
 * Condition for stalemate by three-fold-repetition
 *
 * In chess, the threefold repetition rule states that
 * a player may claim a draw if the same position occurs three times.
 *
 * Two positions are by definition "the same"
 * if the same types of pieces occupy the same squares,
 * the same player has the move,
 * the remaining castling rights are the same
 * and the possibility to capture en passant is the same.
 *
 * The repeated positions need not occur in succession.
 */
class ThreeFoldRepetitionStalemate : EndCondition2D<AbstractChess> {
    override fun evaluate(game: AbstractChess, player: Player, moves: List<Move2D>): Outcome? {
        // We use a pair of the player that has the move and
        // sorted list of valid moves to check if two positions are the same
        val positionCounter: HashMap<Pair<Player, List<Move2D>>, Int> = HashMap()
        val undoneMoves: MutableList<Move2D> = mutableListOf()
        for (i in game.moveLog.indices.reversed()) {
            val move = game.moveLog[i]

            if (move is Move2D.CompositeMove || move.displayPieceCaptured != null) {
                break
            }

            val currentPlayer: Player = game.getCurrentPlayer()
            val validMoves: MutableList<Move2D> = mutableListOf()

            game.players.map { validMoves.addAll(game.getValidMoves(it)) }

            val currentPosition = Pair(currentPlayer, validMoves)
            positionCounter[currentPosition] =
                positionCounter.getOrDefault(currentPosition, 0) + 1
            if (positionCounter[currentPosition] == 3) {
                for (j in undoneMoves.indices.reversed()) {
                    game.makeMove(undoneMoves[j])
                    game.nextPlayer()
                }
                return Outcome.Draw("by Threefold Repetition")
            }

            undoneMoves.add(move)

            game.undoMove()
            game.nextPlayer()
        }

        for (i in undoneMoves.indices.reversed()) {
            game.makeMove(undoneMoves[i])
            game.nextPlayer()
        }
        return null
    }
}
