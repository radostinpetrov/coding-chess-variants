package players

import screens.GameScreen

abstract class FrontendPlayer(val gameScreen: GameScreen) {
    val gameType = gameScreen.gameEngine
    open fun signalTurn() {}
}
