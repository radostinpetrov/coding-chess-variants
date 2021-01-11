package players

import com.badlogic.gdx.graphics.Color
import screens.GameScreen
import screens.GameScreenHexagonal

class NetworkEnemyPlayerHex(game: GameScreenHexagonal, colour: Color, name: String, username: String, elo: Int?) :
    FrontendPlayerHex(game, colour, name, username, elo) {
    fun makeMove(moveIndex: Int) {
        val validMoves = gameType.getValidMoves(libPlayer)
        gameScreen.processTurn(validMoves[moveIndex])
    }

    fun concede() {
        gameScreen.processConcede(libPlayer)
    }
}
