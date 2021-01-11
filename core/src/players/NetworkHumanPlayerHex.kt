package players

import com.badlogic.gdx.graphics.Color
import moves.Move2D
import moves.MoveHex
import screens.GameScreen
import screens.GameScreenHexagonal

class NetworkHumanPlayerHex(gameScreen: GameScreenHexagonal, val websocketClientManager: WebsocketClientManagerHex,
                         colour: Color, name: String, username: String, elo: Int?) :
    HumanPlayerHex(gameScreen, colour, name, username, elo) {
    override fun makeMove(move: MoveHex) {
        val choiceOfMoves = gameType.getValidMoves(libPlayer)
        websocketClientManager.sendPlayerMove(choiceOfMoves.indexOf(move))
        gameScreen.processTurn(move)
    }
}
