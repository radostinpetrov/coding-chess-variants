package main.kotlin.players

import main.kotlin.GameMove
class NetworkHumanPlayer(val websocketClientManager: WebsocketClientManager) : HumanPlayer() {
    private var turnMove: GameMove? = null

    fun setGameMove(move: GameMove) {
        turnMove = move
    }

    override fun getTurn(choiceOfMoves: List<GameMove>): GameMove? {
        if (turnMove == null) {
            return null
        }

        val temp = turnMove
        turnMove = null
        websocketClientManager.sendPlayerMove(choiceOfMoves.indexOf(temp))
        return temp
    }
}
