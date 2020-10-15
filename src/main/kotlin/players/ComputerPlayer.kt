package players

import GameMove

class ComputerPlayer: Player {
    /* Gets a random move from the list of game moves. */
    override fun getTurn(choiceOfMoves: List<GameMove>): GameMove {
        print(choiceOfMoves.size)
        println()
        return choiceOfMoves[(choiceOfMoves.indices).random()]
    }
}