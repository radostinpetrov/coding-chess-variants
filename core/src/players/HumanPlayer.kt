package players

import gameMoves.GameMove2D
import screens.GameScreen

open class HumanPlayer(gameScreen: GameScreen) : FrontendPlayer(gameScreen) {
    open fun makeMove(move: GameMove2D) {
        gameScreen.processTurn(move)
    }
}
