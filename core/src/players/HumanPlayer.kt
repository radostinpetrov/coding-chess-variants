package main.kotlin.players

import main.kotlin.GameMove
import screens.GameScreen

open class HumanPlayer(gameScreen: GameScreen) : SignalPlayer(gameScreen) {
    open fun makeMove(move: GameMove) {
        gameScreen.processTurn(move)
    }
}
