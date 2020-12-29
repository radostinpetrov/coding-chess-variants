package players

import gameMoves.GameMove2D
import screens.GameScreen

class ComputerPlayer(gameScreen: GameScreen, val delay: Long) : FrontendPlayer(gameScreen) {

    override fun signalTurn() {
        val validMoves: List<GameMove2D> = gameType.getValidMoves()
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
