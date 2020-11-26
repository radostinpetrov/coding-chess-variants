package players

import GameMove
import players.HumanPlayer
import players.WebsocketClientManager
import screens.GameScreen

class NetworkHumanPlayer(gameScreen: GameScreen, val websocketClientManager: WebsocketClientManager) : HumanPlayer(gameScreen) {
    override fun makeMove(move: GameMove) {
        val choiceOfMoves = gameType.getValidMoves(this)
        websocketClientManager.sendPlayerMove(choiceOfMoves.indexOf(move))
        gameScreen.processTurn(move)
    }
}
