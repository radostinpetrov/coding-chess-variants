package server.utils

import gameTypes.GameType2D
import kotlinx.coroutines.Job
import org.apache.commons.lang3.time.StopWatch
import java.util.*

data class MatchWrapper(val myUsername: String,
                        val opponentUsername: String,
                        val opponentId: UUID,
                        val game: GameType2D,
                        val myPlayerIndex: Int,
                        var myRemainingTime: Long? = null,
                        var myTimerJob: Job? = null,
                        val stopWatch: StopWatch = StopWatch())