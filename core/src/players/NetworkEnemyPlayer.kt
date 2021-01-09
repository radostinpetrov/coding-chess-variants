package players

import com.badlogic.gdx.graphics.Color
import screens.GameScreen

class NetworkEnemyPlayer(game: GameScreen, colour: Color, name: String, username: String, elo: Int?) :
    FrontendPlayer(game, colour, name, username, elo) {
    fun makeMove(moveIndex: Int) {
        val validMoves = gameType.getValidMoves(libPlayer)
        gameScreen.processTurn(validMoves[moveIndex])
    }

    fun concede() {
        gameScreen.processConcede(libPlayer)
    }
}
