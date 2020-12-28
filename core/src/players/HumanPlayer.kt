package players

import GameMove
import screens.GameScreen

open class HumanPlayer(gameScreen: GameScreen) : FrontendPlayer(gameScreen) {
    open fun makeMove(move: GameMove) {
        gameScreen.processTurn(move)
    }
}
