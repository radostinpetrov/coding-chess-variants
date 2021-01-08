package gameTypes

import Outcome
import boards.Board
import coordinates.Coordinate
import moves.Move
import moveGenerators.MoveGenerator
import pieces.Piece
import players.Player

interface GameType<B : Board<B, MG, M, P, C>, MG : MoveGenerator<B, MG, M, P, C>, M: Move<B, MG, M, P, C>, P: Piece<B, MG, M, P, C>, C: Coordinate> {
    val board: B
    val players: List<Player>
    val name: String
    var playerTurn: Int
    var seed: Double?
    val moveLog: MutableList<M>

    fun initGame()
    fun isOver(): Boolean
    fun getOutcome(player: Player): Outcome?
    fun getOutcome(): Outcome? {
        return getOutcome(getCurrentPlayer())
    }
    fun getValidMoves(player: Player): List<M>
    fun getValidMoves(): List<M> {
        return getValidMoves(getCurrentPlayer())
    }
    fun makeMove(move: M)
    fun concede(player: Player)
    fun undoMove()
    fun inCheck(player: Player) : Boolean

    fun prevPlayer() {
        playerTurn--
        playerTurn += players.size
        playerTurn %= players.size
    }
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