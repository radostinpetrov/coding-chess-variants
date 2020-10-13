package moves

import Board
import Coordinate

interface Move {
    fun getCoordinates(board: Board): List<Coordinate>
}