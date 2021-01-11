package players

import com.badlogic.gdx.graphics.Color
import moves.Move2D
import moves.MoveHex
import screens.GameScreen
import screens.GameScreenHexagonal

/**
 * Human player class that extends FrontendPlayer. Used to process turn with moves dictated by the controls.
 */
open class HumanPlayerHex(gameScreen: GameScreenHexagonal, colour: Color, name: String, username: String = "Human Player",
                       elo: Int? = null) : FrontendPlayerHex(
    gameScreen, colour, name, username, elo
) {
    /**
     * Tells the gameScreen a valid move to play.
     * @param move HumanPlayer's chosen move.
     */
    open fun makeMove(move: MoveHex) {
        gameScreen.processTurn(move)
    }
}
