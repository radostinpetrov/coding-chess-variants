package gameTypes

import moves.Move

interface GameType {
    fun initGame()
    fun isOver(): Boolean
    // fun getWinner()
    fun canMove(move: Move): Boolean
    fun getHistory(): List<Move>
    fun makeMove(move: Move)
}
