package main.kotlin.players

import main.kotlin.GameMove

class NetworkEnemyPlayer : HumanPlayer() {
    private var turnMoveIndex: Int? = null

    fun setGameMove(moveIndex: Int) {
        turnMoveIndex = moveIndex
    }

    override fun getTurn(choiceOfMoves: List<GameMove>): GameMove? {
        if (turnMoveIndex == null) {
            return null
        }

        val temp = choiceOfMoves[turnMoveIndex!!]
        turnMoveIndex = null

        return temp
    }
}
