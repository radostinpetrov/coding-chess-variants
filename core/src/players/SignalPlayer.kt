package main.kotlin.players

import players.Player
import screens.GameScreen

abstract class SignalPlayer(val gameScreen: GameScreen) : Player {
    val gameType = gameScreen.gameEngine
    open fun signalTurn() {}
}