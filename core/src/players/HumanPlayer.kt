package players

import com.badlogic.gdx.graphics.Color
import moves.Move2D
import screens.GameScreen

open class HumanPlayer(gameScreen: GameScreen, colour: Color, name: String) : FrontendPlayer(
    gameScreen, colour, name
) {
    open fun makeMove(move: Move2D) {
        gameScreen.processTurn(move)
    }
}
