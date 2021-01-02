package players

import com.badlogic.gdx.graphics.Color
import screens.GameScreen

class NetworkEnemyPlayer(game: GameScreen, colour: Color, name: String) : FrontendPlayer(game, colour, name) {
    fun makeMove(moveIndex: Int) {
        val validMoves = gameType.getValidMoves(libPlayer)
        gameScreen.processTurn(validMoves[moveIndex])
    }
}
