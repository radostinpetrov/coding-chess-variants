package gameTypes

import Board
import Coordinate
import moves.Move

interface GameType {
    fun initGame()
    fun isOver(): Boolean
    // fun getWinner()
    fun canMove(originalCoordinate: Coordinate, finalCoordinate: Coordinate): Boolean
    fun getHistory(): List<Board>
    fun makeMove(move: Move)
}
