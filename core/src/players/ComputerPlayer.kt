package players

import GameMove
import screens.GameScreen

class ComputerPlayer(gameScreen: GameScreen, val delay: Long) : SignalPlayer(gameScreen) {

    override fun signalTurn() {
        var validMoves: List<GameMove> = listOf()
        validMoves = gameType.getValidMoves(this)

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
