package players

import com.badlogic.gdx.graphics.Color
import screens.GameScreen

abstract class FrontendPlayer(val gameScreen: GameScreen, val colour: Color, val name: String) {
    val gameType = gameScreen.gameEngine
    // TODO any way to guarantee this to be initialized?
    lateinit var libPlayer : Player
    var endClock: Int? = null
    open fun signalTurn() {}

    var initialClock = 0
}
