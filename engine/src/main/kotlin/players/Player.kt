package players

import GameMove

interface Player {
    // Returns the chosen move of the player.
    fun getTurn(choiceOfMoves: List<GameMove>): GameMove
}
