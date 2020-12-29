package players

import screens.GameScreen

class NetworkEnemyPlayer(game: GameScreen) : FrontendPlayer(game) {
    fun makeMove(moveIndex: Int) {
        val validMoves = gameType.getValidMoves()
        gameScreen.processTurn(validMoves[moveIndex])
    }
}
