package players

import screens.GameScreen

abstract class SignalPlayer(val gameScreen: GameScreen) : Player {
    val gameType = gameScreen.gameEngine
    open fun signalTurn() {}
}
