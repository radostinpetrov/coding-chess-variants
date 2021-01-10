package gameTypes

import boards.Board
import coordinates.Coordinate
import endconditions.EndCondition
import endconditions.Outcome
import moves.Move2D.SimpleMove.*
import moveGenerators.MoveGenerator
import moves.Move
import pieces.Piece
import players.Player
import rules.SpecialRules

/**
 * Represents a standard n-player game,
 * which contains no special rules and standard end conditions.
 *
 * @property rules the list of special rules (e.g. Castling, EnPassant)
 * @property endConditions the list of conditions that will end the game
 */


abstract class GameType2P<B : Board<B, MG, M, P, C>,
        MG : MoveGenerator<B, MG, M, P, C>,
        M: Move<B, MG, M, P, C>,
        P: Piece<B, MG, M, P, C>,
        C: Coordinate>
    : GameType<B, MG, M, P, C> {
    override val players: List<Player> = listOf(Player(), Player())
    override var playerTurn: Int = 0
    // This is set as the winner when either player concedes
    private var concededWinner: Player? = null

    override var seed: Double? = null

    override val moveLog: MutableList<M> = mutableListOf()

    override fun isOver(): Boolean {
        return getOutcome(getCurrentPlayer()) != null
    }

    override fun initGame() {
        board.clearBoard()
        initBoard()
    }

    override fun getOutcome(player: Player): Outcome? {
        val curConcededWinner = concededWinner
        if (curConcededWinner != null) {
            return Outcome.Win(curConcededWinner, "by opponent concede")
        }
        val moves = getValidMoves(player)
        for (ec in endConditions) {
            val outcome = ec.evaluate(this, player, moves)
            if (outcome != null) {
                return outcome
            }
        }
        return null
    }

    protected fun getValidMoveForPiece(pair: Pair<P, C>): List<M> {
        val possibleMoves = mutableListOf<M>()
        // validate possible moves

        val piece = pair.first
        val coordinate = pair.second

        for (move in piece.moveGenerators) {
            possibleMoves.addAll(move.generate(board, coordinate, piece, getCurrentPlayer()))
        }

        return possibleMoves
    }

    override fun concede(player: Player) {
        concededWinner = getOpponentPlayer(player)
    }

    fun getOpponentPlayer(player: Player): Player {
        return getOpponentPlayers(player)[0]
    }
}
