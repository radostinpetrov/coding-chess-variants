package main.kotlin.players

import main.kotlin.GameMove

open class HumanPlayer : Player {
    override var playerMove: GameMove? = null

    override fun getTurn(choiceOfMoves: List<GameMove>): GameMove? {
        val temp = playerMove
        playerMove = null
        if (temp != null) {
            playerMove = null
        }
        return temp
    }
}
