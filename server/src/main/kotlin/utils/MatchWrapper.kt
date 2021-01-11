package server.utils

import boards.Board
import coordinates.Coordinate
import gameTypes.AbstractChess
import kotlinx.coroutines.Job
import moveGenerators.MoveGenerator
import org.apache.commons.lang3.time.StopWatch
import pieces.Piece
import java.util.*

data class MatchWrapper<G: AbstractChess<B, MG, P, C>,
        B : Board<B, MG, P, C>,
        MG : MoveGenerator<B, MG, P, C>,
        P : Piece<B, MG, P, C>,
        C : Coordinate>
    (val myUsername: String,
     val opponentUsername: String,
     val opponentId: UUID,
     val game: G,
     val myPlayerIndex: Int,
     var myRemainingTime: Long? = null,
     var myTimerJob: Job? = null,
     val stopWatch: StopWatch = StopWatch()) {

    // Check if move is valid and make move if it is
    // Returns true if move was valid and the move was made, false otherwise
    fun makeValidMove(moveIndex: Int): Boolean {
        if (game.playerTurn != myPlayerIndex) {
            println("Error: Not this player's turn")
            return false
        }

        val validMoves = game.getValidMoves()

        // Check if valid move
        if (moveIndex < 0 || moveIndex >= validMoves.size) {
            println("Error: Invalid move")
            return false
        }

        game.playerMakeMove(validMoves[moveIndex])
        return true
    }
}