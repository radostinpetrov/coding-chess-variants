package moves

import Board
import Coordinate
import Direction

class Stepper(val dir:Direction, val dy:Int): Move {
    override fun getCoordinates(board: Board, from: Coordinate): List<Coordinate> {
        TODO("Not yet implemented")
    }
}