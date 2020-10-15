package gameTypes

import Coordinate
import boards.Board
import GameMove
import boards.Board2D
//import History
import pieces.Piece
import players.Player

interface GameType {
    val board: Board2D
//    val history: MutableList<History>
    val players: MutableList<Player>
    var playerTurn: Int


    fun initGame()
    fun isOver(): Boolean
    // fun getWinner()
    fun getValidMoves(player: Player): List<GameMove>

    //fun getHistory(): List<Pair<Board, GameMove>>
    fun makeMove(gameMove: GameMove)
    fun addPlayer(player: Player)
    fun turn()
    fun nextPlayer()
    fun checkValidGame(): Boolean
}
