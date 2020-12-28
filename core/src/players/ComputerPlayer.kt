package players

import GameMove
import screens.GameScreen

class ComputerPlayer(gameScreen: GameScreen, val delay: Long) : FrontendPlayer(gameScreen) {

    override fun signalTurn() {
        val validMoves: List<GameMove> = gameType.getValidMoves(gameScreen.frontendToLibPlayer[this]!!)
//        println("Computer is thinking...")
//        // TODO FIX COMPUTER PLAYER LAG SITUATION
//        Thread.sleep(100)
//        gameScreen.processTurn(validMoves[(validMoves.indices).random()])

        val runnable = Runnable {
            Thread.sleep(delay)
            gameScreen.processTurn(validMoves[(validMoves.indices).random()])
        }
        val threadWithRunnable = Thread(runnable)
        threadWithRunnable.start()
    }
}
