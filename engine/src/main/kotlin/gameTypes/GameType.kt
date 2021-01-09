package gameTypes

import endconditions.Outcome
import boards.Board
import coordinates.Coordinate
import moves.Move
import moveGenerators.MoveGenerator
import pieces.Piece
import players.Player

/**
 * Represents a game variant (e.g. Standard Chess, Capablanca Chess, Xiangqi).
 *
 * Controls the flow of a game and keeps track of all the moves in the move log,
 * which player has the current turn and the final result of the game.
 *
 * @param B the type of a board.
 * @param MG the type of a move generator.
 * @param M the type of a move.
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
interface GameType<B : Board<B, MG, M, P, C>, MG : MoveGenerator<B, MG, M, P, C>, M: Move<B, MG, M, P, C>, P: Piece<B, MG, M, P, C>, C: Coordinate> {
    val board: B
    val players: List<Player>
    val name: String
    var playerTurn: Int
    var seed: Double?
    val moveLog: MutableList<M>

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
     * @return true if the game is over
     */
    fun isOver(): Boolean

    /**
     * @return the outcome of the game for the given player,
     * which can be either Win or Draw (null if the game is not over)
     */
    fun getOutcome(player: Player): Outcome?

    /**
     * @return the outcome of the game for the current player,
     * which can be either Win or Draw (null if the game is not over)
     */
    fun getOutcome(): Outcome? {
        return getOutcome(getCurrentPlayer())
    }

    /**
     * @return a list of all possible valid moves of the given player
     */
    fun getValidMoves(player: Player): List<M>

    /**
     * @return a list of all possible valid moves of the current player
     */
    fun getValidMoves(): List<M> {
        return getValidMoves(getCurrentPlayer())
    }

    /**
     * Makes the given move on the current board and
     * add the move to the move log.
     */
    fun makeMove(move: M)

    /**
     * Sets the concedeWinner to opponent (a player that is not current player)
     */
    fun concede(player: Player)

    /**
     * Reverts the last move and removes it from the move log.
     */
    fun undoMove()

    /**
     * @return true if the given player’s King is in check.
     */
    fun inCheck(player: Player) : Boolean

    /**
     * @return the opponent player for a given player.
     */
    fun getOpponentPlayers(player: Player): List<Player> {
        return players.filter{p -> p != player}
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
     * @return the current player
     */
    fun getCurrentPlayer(): Player {
        return players[playerTurn]
    }

    /**
     * @return the next player without incrementing the turn.
     */
    fun getNextPlayer(): Player {
        return players[(playerTurn + 1) % players.size]
    }

    /**
     * Makes a given move and increments the turn
     */
    fun playerMakeMove(move: M) {

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