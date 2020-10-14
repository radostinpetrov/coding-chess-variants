package gameTypes

import Coordinate
import boards.Board
import GameMove
import History

interface GameType {
    val board: Board
    val history: MutableList<History>

    fun initGame()
    fun isOver(): Boolean
    // fun getWinner()
    fun getValidMoves(coordinate: Coordinate): List<GameMove>

    //fun getHistory(): List<Pair<Board, GameMove>>
    fun makeMove(gameMove: GameMove)
}
