package players

import GameMove

class ComputerPlayer(val delay: Long) : ConsolePlayer() {
    override var playerMove: GameMove? = null

    override fun getTurn(choiceOfMoves: List<GameMove>): GameMove? {
        println("Computer is thinking...")
        Thread.sleep(0)
        return choiceOfMoves[(choiceOfMoves.indices).random()]
    }
}
