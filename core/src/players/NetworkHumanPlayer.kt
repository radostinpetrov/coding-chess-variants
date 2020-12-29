package players

import gameMoves.GameMove2D
import screens.GameScreen

class NetworkHumanPlayer(gameScreen: GameScreen, val websocketClientManager: WebsocketClientManager) : HumanPlayer(gameScreen) {
    override fun makeMove(move: GameMove2D) {
        val choiceOfMoves = gameType.getValidMoves(gameScreen.frontendToLibPlayer[this]!!)
        websocketClientManager.sendPlayerMove(choiceOfMoves.indexOf(move))
        gameScreen.processTurn(move)
    }
}
