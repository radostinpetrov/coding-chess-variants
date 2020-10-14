package moves

import Coordinate
import Direction
import GameMove
import gameTypes.GameType

sealed class Move {
    // checks for valid moves other than checkmate, stalemate, en passant and castles
    data class Slider(val directions: Array<Direction>): Move()
    data class Stepper(val direction: Direction): Move()
    data class Leaper(val dx: Int, val dy: Int): Move()
}

//sealed class
