package main.kotlin.players

import main.kotlin.GameMove

abstract class HumanPlayer : Player {
    abstract override fun getTurn(choiceOfMoves: List<GameMove>): GameMove?
}
