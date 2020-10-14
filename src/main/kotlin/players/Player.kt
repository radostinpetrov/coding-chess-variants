package players

import GameMove

interface Player {
    //returns the valid chosen move
    fun getTurn(choiceOfMoves: List<GameMove>): GameMove
}