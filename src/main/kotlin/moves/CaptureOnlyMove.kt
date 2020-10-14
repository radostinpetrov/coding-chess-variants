package moves

import Coordinate
import GameMove
import gameTypes.GameType

class CaptureOnlyMove(val move: Move): Move {
    override fun getPossibleMoves(gameType: GameType, from: Coordinate): List<GameMove> {
        TODO("Not yet implemented")
    }
}