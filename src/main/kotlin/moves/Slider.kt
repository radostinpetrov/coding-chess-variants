package moves

import Board
import Coordinate

class Slider(val H: Boolean, val V: Boolean, val A: Boolean, val  D: Boolean): Move {
    override fun getCoordinates(board: Board, from: Coordinate): List<Coordinate> {
        TODO("Not yet implemented")
    }
}