package players

import com.badlogic.gdx.graphics.Color
import org.apache.commons.lang3.time.StopWatch
import screens.GameScreen

/**
 * Abstract class that is used to represent a player. Extend this class to make your own type of
 * player. eg. Computer player, Human player (using the mouse as input commands).
 */
abstract class FrontendPlayer(val gameScreen: GameScreen, val colour: Color, val name: String, val username: String,
                              val elo: Int? = null) {
    val gameType = gameScreen.gameEngine
    // TODO any way to guarantee this to be initialized?
    lateinit var libPlayer : Player
    var endClock: Long? = null
    open fun signalTurn() {}

    var stopwatch = StopWatch()
    var elapsedTime: Long = 0

    fun getRemainingTime() : Long {
        val remainingTime = (endClock!! * 1000L - elapsedTime - stopwatch.time) / 1000L
        if (remainingTime < 0) {
            return 0
        }
        return remainingTime
    }

    fun syncElapsedTime(remainingTime: Long?) {
        if (remainingTime != null && endClock != null) {
            elapsedTime = (endClock!! * 1000L) - remainingTime

        }
    }

    fun flipClock() {
        if (stopwatch.isStarted) {
            stopwatch.stop()
            elapsedTime += stopwatch.time
            stopwatch.reset()
        } else {
            stopwatch.start()
        }
    }
}
