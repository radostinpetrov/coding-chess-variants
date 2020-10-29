package main.kotlin.players

import main.kotlin.GameMove

abstract class HumanPlayer : Player {
    fun isInteger(s: String?) = s?.toIntOrNull()?.let { true } ?: false

    abstract override fun getTurn(choiceOfMoves: List<GameMove>): GameMove?
}
