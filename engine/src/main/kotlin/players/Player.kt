package main.kotlin.players

import main.kotlin.GameMove

interface Player {
    var playerMove: GameMove?

    // Returns the chosen move of the player.
    fun getTurn(choiceOfMoves: List<GameMove>): GameMove?
}
