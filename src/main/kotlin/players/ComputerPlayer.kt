package players

import GameMove

class ComputerPlayer: Player {
    override fun getTurn(choiceOfMoves: List<GameMove>): GameMove {
        return choiceOfMoves[(0 .. choiceOfMoves.size - 1).random()]
    }
}