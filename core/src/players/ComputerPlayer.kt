package main.kotlin.players

import main.kotlin.GameMove

class ComputerPlayer(val delay: Long) : Player {
    override var playerMove: GameMove? = null

    override fun getTurn(choiceOfMoves: List<GameMove>): GameMove? {
        println("Computer is thinking...")
        Thread.sleep(delay)
        return choiceOfMoves[(choiceOfMoves.indices).random()]
    }
}
