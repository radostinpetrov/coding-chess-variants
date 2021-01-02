package players

import com.badlogic.gdx.graphics.Color
import gameMoves.GameMove2D
import screens.GameScreen

class NetworkHumanPlayer(gameScreen: GameScreen, val websocketClientManager: WebsocketClientManager,
                         colour: Color) : HumanPlayer(gameScreen, colour) {
    override fun makeMove(move: GameMove2D) {
        val choiceOfMoves = gameType.getValidMoves(libPlayer)
        websocketClientManager.sendPlayerMove(choiceOfMoves.indexOf(move))
        gameScreen.processTurn(move)
    }
}
