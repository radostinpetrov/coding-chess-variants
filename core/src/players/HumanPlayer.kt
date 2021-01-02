package players

import com.badlogic.gdx.graphics.Color
import gameMoves.GameMove2D
import screens.GameScreen

open class HumanPlayer(gameScreen: GameScreen, colour: Color, name: String) : FrontendPlayer(
    gameScreen, colour, name
) {
    open fun makeMove(move: GameMove2D) {
        gameScreen.processTurn(move)
    }
}
