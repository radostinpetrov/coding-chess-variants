package players

import com.badlogic.gdx.graphics.Color
import moves.Move2D
import screens.GameScreen

/**
 * Human player class that extends FrontendPlayer. Used to process turn with moves dictated by the controls.
 */
open class HumanPlayer(
    gameScreen: GameScreen,
    colour: Color,
    name: String,
    username: String = "Human Player",
    elo: Int? = null
) : FrontendPlayer(
    gameScreen, colour, name, username, elo
) {
    /**
     * Tells the gameScreen a valid move to play.
     * @param move HumanPlayer's chosen move.
     */
    open fun makeMove(move: Move2D) {
        gameScreen.processTurn(move)
    }
}
