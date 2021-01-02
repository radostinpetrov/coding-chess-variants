package gameTypes.chess.winconditions

import Outcome
import gameMoves.GameMove2D
import gameTypes.chess.AbstractChess
import players.Player

/***
 * In chess, the threefold repetition rule states that
 * a player may claim a draw if the same position occurs three times.
 *
 * Two positions are by definition "the same"
 * if the same types of pieces occupy the same squares,
 * the same player has the move,
 * the remaining castling rights are the same
 * and the possibility to capture en passant is the same.
 *
 * The repeated positions need not occur in succession. ***/
class ThreeFoldRepetitionStalemate : WinCondition<AbstractChess> {
    override fun evaluate(game: AbstractChess, player: Player, moves: List<GameMove2D>): Outcome? {
        // Keep track of count of (player -> sorted list of valid moves) hashmap
        val positionCounter: HashMap<HashMap<Player, List<GameMove2D>>, Int> = HashMap()
        val undoneMoves: MutableList<GameMove2D> = mutableListOf()

        for (i in game.moveLog.indices.reversed()) {
            val move = game.moveLog[i]

            val currentPosition: HashMap<Player, List<GameMove2D>> = HashMap()
            game.players.map { player ->
                currentPosition[player] = game.getValidMoves(player)
                    .sortedBy { it.hashCode() }
            }

            positionCounter[currentPosition] =
                positionCounter.getOrDefault(currentPosition, 0) + 1
            if (positionCounter[currentPosition] == 3) {
                for (j in undoneMoves.indices.reversed()) {
                    game.makeMove(undoneMoves[j])
                }
                return Outcome.Draw("Stalemate by Threefold Repetition")
            }

            undoneMoves.add(move)
            game.undoMove()

            if (move.displayPieceCaptured != null) {
                break
            }
        }

        for (i in undoneMoves.indices.reversed()) {
            game.makeMove(undoneMoves[i])
        }
        return null
    }
}
