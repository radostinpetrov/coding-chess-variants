package main.kotlin.players

import screens.GameScreen

class NetworkEnemyPlayer(game: GameScreen) : SignalPlayer(game) {
    fun makeMove(moveIndex: Int) {
        val validMoves = gameType.getValidMoves(this)
        gameScreen.processTurn(validMoves[moveIndex])
    }
}
