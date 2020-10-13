package moves

import Board
import Coordinate

interface Move {
    fun getCoordinates(board: Board, from: Coordinate): List<Coordinate>
}