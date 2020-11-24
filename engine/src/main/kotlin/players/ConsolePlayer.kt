package main.kotlin.players

import main.kotlin.GameMove

abstract class ConsolePlayer: Player {
    abstract var playerMove: GameMove?

    // Returns the chosen move of the player.
    abstract fun getTurn(choiceOfMoves: List<GameMove>): GameMove?
}