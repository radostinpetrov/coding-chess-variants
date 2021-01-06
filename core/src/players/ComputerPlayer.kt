package players

import com.badlogic.gdx.graphics.Color
import moves.Move2D
import screens.GameScreen

class ComputerPlayer(gameScreen: GameScreen, val delay: Long, colour: Color, name: String) : FrontendPlayer(
    gameScreen, colour, name) {

    override fun signalTurn() {
        val validMoves: List<Move2D> = gameType.getValidMoves(libPlayer)
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
