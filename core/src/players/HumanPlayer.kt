package players

import com.badlogic.gdx.graphics.Color
import gameMoves.GameMove2D
import screens.GameScreen

open class HumanPlayer(gameScreen: GameScreen, colour: Color) : FrontendPlayer(
    gameScreen, colour
) {
    open fun makeMove(move: GameMove2D) {
        gameScreen.processTurn(move)
    }
}
