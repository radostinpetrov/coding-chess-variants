package moves

import Coordinate
import GameMove
import gameTypes.GameType

class Leaper(val dx: Int, val dy: Int) : Move {
    override fun getPossibleMoves(gameType: GameType, from: Coordinate): List<GameMove> {
        TODO("Not yet implemented")
    }
}