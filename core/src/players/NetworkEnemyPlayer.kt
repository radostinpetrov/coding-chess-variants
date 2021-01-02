package players

import com.badlogic.gdx.graphics.Color
import screens.GameScreen

class NetworkEnemyPlayer(game: GameScreen, colour: Color) : FrontendPlayer(game, colour) {
    fun makeMove(moveIndex: Int) {
        val validMoves = gameType.getValidMoves(libPlayer)
        gameScreen.processTurn(validMoves[moveIndex])
    }
}
