package players

import com.badlogic.gdx.graphics.Color
import moves.Move2D
import screens.GameScreen

/**
 * Computer player class that extends FrontendPlayer. It chooses a random a move to make from the valid
 * moves list.
 */
class ComputerPlayer(gameScreen: GameScreen, val delay: Long, colour: Color, name: String) : FrontendPlayer(
    gameScreen, colour, name, "Computer Player"
) {

    /**
     * Tells the gameScreen a random valid move to play.
     */
    override fun signalTurn() {
        val validMoves: List<Move2D> = gameType.getValidMoves(libPlayer)
//        println("Computer is thinking...")
//        // TODO FIX COMPUTER PLAYER LAG SITUATION
//        Thread.sleep(100)
//        gameScreen.processTurn(validMoves[(validMoves.indices).random()])

        if (validMoves.isEmpty()) {
            return
        }

        val runnable = Runnable {
            Thread.sleep(delay)
            gameScreen.processTurn(validMoves[(validMoves.indices).random()])
        }
        val threadWithRunnable = Thread(runnable)
        threadWithRunnable.start()
    }
}
