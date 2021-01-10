package gameTypes

import boards.Board
import coordinates.Coordinate
import endconditions.EndCondition
import endconditions.Outcome
import moveGenerators.MoveGenerator
import moves.Move
import pieces.Piece
import players.Player
import rules.SpecialRules

/**
 * Represents a game variant.
 *
 * Controls the flow of a game and keeps track of all the moves in the move log,
 * which player has the current turn and the final result of the game.
 *
 * @param B the type of a board.
 * @param MG the type of a move generator.
 * @param P the type of a piece.
 * @param C the type of a coordinate.
 *
 * @property board the board of the game
 * @property players the list of all participants of the game
 * @property name the name of the game
 * @property playerTurn the current turn of the game
 * @property seed the random seed
 * @property moveLog the list of all moves played
 */
interface GameType<B : Board<B, MG, P, C>, MG : MoveGenerator<B, MG, P, C>, P : Piece<B, MG, P, C>, C : Coordinate> {
    val board: B
    val players: List<Player>
    val name: String
    var playerTurn: Int
    var seed: Double?
    val moveLog: MutableList<Move<B, MG, P, C>>
    val rules: List<SpecialRules<GameType<B, MG, P, C>, B, MG, P, C>>
    val endConditions: List<EndCondition<GameType<B, MG, P, C>, B, MG, P, C>>

    /**
     * Initialises the game by clearing and initialising the board
     */
    fun initGame()

    /**
     * Initialises the board
     * Places pieces on the initial position of the board
     */
    fun initBoard()

    /**
     * Returns if the game is over or not
     *
     * @return true if the game is over
     */
    fun isOver(): Boolean

    /**
     * Returns the outcome of the game for the given player,
     * which can be either Win or Draw (null if the game is not over)
     *
     * @return the outcome of the game for the given player
     */
    fun getOutcome(player: Player): Outcome?

    /**
     * Returns the outcome of the game for the current player,
     * which can be either Win or Draw (null if the game is not over)
     *
     * @return the outcome of the game for the current player
     */
    fun getOutcome(): Outcome? {
        return getOutcome(getCurrentPlayer())
    }

    /**
     * Returns a list of all possible valid moves of the given player
     *
     * @return a list of all possible valid moves of the given player
     */
    fun getValidMoves(player: Player): List<Move<B, MG, P, C>>

    /**
     * Returns a list of all possible valid moves of the current player
     *
     * @return a list of all possible valid moves of the current player
     */
    fun getValidMoves(): List<Move<B, MG, P, C>> {
        return getValidMoves(getCurrentPlayer())
    }

    /**
     * Makes the given move on the current board and
     * add the move to the move log.
     */
    fun makeMove(move: Move<B, MG, P, C>)

    /**
     * Sets the concedeWinner to opponent (a player that is not current player)
     */
    fun concede(player: Player)

    /**
     * Reverts the last move and removes it from the move log.
     */
    fun undoMove()

    /**
     * Returns if the given player's Royal piece is is check or not,
     * assuming that the Royal piece is present on the board.
     *
     * @return true if the given player’s Royal piece is in check.
     */
    fun inCheck(player: Player): Boolean

    /**
     * Returns a list of opponent players for a given player.
     *
     * @return a list of opponent players for a given player.
     */
    fun getOpponentPlayers(player: Player): List<Player> {
        return players.filter { p -> p != player }
    }

    /**
     * Decrements the turn
     */
    fun prevPlayer() {
        playerTurn--
        playerTurn += players.size
        playerTurn %= players.size
    }

    /**
     * Increments the turn
     */
    fun nextPlayer() {
        playerTurn++
        playerTurn %= players.size
    }

    /**
     * Returns the current player
     *
     * @return the current player
     */
    fun getCurrentPlayer(): Player {
        return players[playerTurn]
    }

    /**
     * Returns the next player without incrementing the turn.
     *
     * @return the next player without incrementing the turn.
     */
    fun getNextPlayer(): Player {
        return players[(playerTurn + 1) % players.size]
    }

    /**
     * Makes a given move and increments the turn
     */
    fun playerMakeMove(move: Move<B, MG, P, C>) {

        // TODO discuss if we should keep this first check
//        val validMoves = getValidMoves(getCurrentPlayer())
//
//        if (!validMoves.contains(move)) {
//            return
//        }

        makeMove(move)
        nextPlayer()
    }
}
