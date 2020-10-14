package moves

import Coordinate
import GameMove
import gameTypes.GameType

class Slider(val H: Boolean, val V: Boolean, val A: Boolean, val  D: Boolean): Move {
    override fun getPossibleMoves(gameType: GameType, from: Coordinate): List<GameMove> {
        TODO("Not yet implemented")
    }
}