package gameTypes

import Outcome
import boards.Board
import coordinates.Coordinate
import gameMoves.GameMove
import moves.Move
import pieces.Piece
import players.Player

interface GameType<B : Board<B, M, GM, P, C>, M : Move<B, M, GM, P, C>, GM: GameMove<B, M, GM, P, C>, P: Piece<B, M, GM, P, C>, C: Coordinate> {
    val board: B
    val players: List<Player>
    var playerTurn: Int
    var seed: Double?
    val moveLog: MutableList<GM>

    fun initGame()
    fun isOver(): Boolean
    fun getOutcome(player: Player): Outcome?
    fun getOutcome(): Outcome? {
        return getOutcome(getCurrentPlayer())
    }
    fun getValidMoves(player: Player): List<GM>
    fun getValidMoves(): List<GM> {
        return getValidMoves(getCurrentPlayer())
    }
    fun makeMove(gameMove: GM)
    fun undoMove()
    fun inCheck(player: Player) : Boolean
    fun nextPlayer() {
        playerTurn++
        playerTurn %= players.size
    }
    fun getCurrentPlayer(): Player {
        return players[playerTurn]
    }
    fun getNextPlayer(): Player {
        return players[(playerTurn + 1) % players.size]
    }
    fun playerMakeMove(move: GM) {

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