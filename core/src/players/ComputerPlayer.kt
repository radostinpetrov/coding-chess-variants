package players

import screens.GameScreen

class ComputerPlayer(gameScreen: GameScreen, val delay: Long) : SignalPlayer(gameScreen) {

    override fun signalTurn() {
        val validMoves = gameType.getValidMoves(this)
        val runnable = Runnable {
            Thread.sleep(delay)
            gameScreen.processTurn(validMoves[(validMoves.indices).random()])
        }
        val threadWithRunnable = Thread(runnable)
        threadWithRunnable.start()
    }
}
