package main.kotlin.players

import screens.GameScreen

class ComputerPlayer(gameScreen: GameScreen, val delay: Long) : SignalPlayer(gameScreen) {
    override fun signalTurn() {
        val validMoves = gameType.getValidMoves(this)
        println("Computer is thinking...")
        // TODO FIX COMPUTER PLAYER LAG SITUATION
//        Thread.sleep(delay)
        gameScreen.processTurn(validMoves[(validMoves.indices).random()])

    }
}
