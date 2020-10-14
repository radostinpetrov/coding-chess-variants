package moves

import Coordinate
import GameMove
import gameTypes.GameType

// make
enum class Direction(coordinate: Coordinate) {
    NORTH(Coordinate(0,1)) {
        override fun rotate(dx : Int, dy: Int): Coordinate {
            return Coordinate(dx, dy)
        }
    },
    NORTHWEST(Coordinate(-1,1)) {
        override fun rotate(dx : Int, dy: Int): Coordinate {
            return TODO()
        }
    },
    EAST(Coordinate(1,0)) {
        override fun rotate(dx : Int, dy: Int): Coordinate {
            return Coordinate(-dy, dx)
        }
    },
    NORTHEAST(Coordinate(1,1)) {
        override fun rotate(dx : Int, dy: Int): Coordinate {
            return TODO()
        }
    },
    SOUTH(Coordinate(0,-1)) {
        override fun rotate(dx : Int, dy: Int): Coordinate {
            return Coordinate(-dx, -dy)
        }
    },
    SOUTHEAST(Coordinate(1,-1)) {
        override fun rotate(dx : Int, dy: Int): Coordinate {
            return TODO()
        }
    },
    WEST(Coordinate(-1, 0)) {
        override fun rotate(dx : Int, dy: Int): Coordinate {
            return Coordinate(dy, -dx)
        }
    },
    SOUTHWEST(Coordinate(-1,-1)) {
        override fun rotate(dx : Int, dy: Int): Coordinate {
            return TODO()
        }
    };

    abstract fun rotate(dx : Int, dy: Int): Coordinate
}

//sealed class in move instead of