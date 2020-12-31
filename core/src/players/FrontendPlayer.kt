package players

import screens.GameScreen

abstract class FrontendPlayer(val gameScreen: GameScreen) {
    val gameType = gameScreen.gameEngine
    // TODO any way to guarantee this to be initialized?
    lateinit var libPlayer : Player
    open fun signalTurn() {}
}
