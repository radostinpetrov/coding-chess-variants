package main.kotlin.players

import main.kotlin.GameMove
import screens.GameScreen

class NetworkHumanPlayer(gameScreen: GameScreen, val websocketClientManager: WebsocketClientManager) : HumanPlayer(gameScreen) {
    override fun makeMove(move: GameMove) {
        val choiceOfMoves = gameType.getValidMoves(this)
        websocketClientManager.sendPlayerMove(choiceOfMoves.indexOf(move))
        gameScreen.processTurn(move)
    }
}
