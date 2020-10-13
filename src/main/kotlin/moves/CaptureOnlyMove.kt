package moves

import Board
import Coordinate

class CaptureOnlyMove(val move: Move): Move {
    override fun getCoordinates(board: Board, from: Coordinate): List<Coordinate> {
        TODO("Not yet implemented")
    }
}