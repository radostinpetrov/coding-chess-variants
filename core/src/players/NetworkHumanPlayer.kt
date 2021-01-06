package players

import com.badlogic.gdx.graphics.Color
import moves.Move2D
import screens.GameScreen

class NetworkHumanPlayer(gameScreen: GameScreen, val websocketClientManager: WebsocketClientManager,
                         colour: Color, name: String) : HumanPlayer(gameScreen, colour, name) {
    override fun makeMove(move: Move2D) {
        val choiceOfMoves = gameType.getValidMoves(libPlayer)
        websocketClientManager.sendPlayerMove(choiceOfMoves.indexOf(move))
        gameScreen.processTurn(move)
    }
}
