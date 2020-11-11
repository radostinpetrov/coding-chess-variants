package main.kotlin.players

import main.kotlin.GameMove
class NetworkHumanPlayer(val websocketClientManager: WebsocketClientManager) : HumanPlayer() {
    override var playerMove: GameMove? = null

    override fun getTurn(choiceOfMoves: List<GameMove>): GameMove? {
        if (playerMove == null) {
            return null
        }

        val temp = playerMove
        playerMove = null
        websocketClientManager.sendPlayerMove(choiceOfMoves.indexOf(temp))
        return temp
    }
}
