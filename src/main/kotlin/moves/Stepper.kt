package moves

import Coordinate
import GameMove
import Direction
import gameTypes.GameType

class Stepper(val dir: Direction, val step: Int): Move {
    override fun getPossibleMoves(gameType: GameType, from: Coordinate): List<GameMove> {
        TODO("Not yet implemented")
    }
}