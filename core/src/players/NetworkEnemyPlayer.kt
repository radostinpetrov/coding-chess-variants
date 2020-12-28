package players

import screens.GameScreen

class NetworkEnemyPlayer(game: GameScreen) : FrontendPlayer(game) {
    fun makeMove(moveIndex: Int) {
        val validMoves = gameType.getValidMoves(gameScreen.frontendToLibPlayer[this]!!)
        gameScreen.processTurn(validMoves[moveIndex])
    }
}
