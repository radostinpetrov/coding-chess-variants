package players

import com.badlogic.gdx.graphics.Color
import screens.GameScreen

/**
 * Abstract class that is used to represent a player. Extend this class to make your own type of
 * player. eg. Computer player, Human player (using the mouse as input commands).
 */
abstract class FrontendPlayer(val gameScreen: GameScreen, val colour: Color, val name: String) {
    val gameType = gameScreen.gameEngine
    // TODO any way to guarantee this to be initialized?
    lateinit var libPlayer : Player
    var endClock: Int? = null
    open fun signalTurn() {}

    var initialClock = 0
}
