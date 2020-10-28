package main.kotlin.moves

import main.kotlin.Coordinate

enum class Direction(val coordinate: Coordinate) {
    NORTH_WEST(Coordinate(-1, 1)) {
        override fun rotate(dx : Int, dy: Int): Coordinate {
            return Coordinate(dx, dy)
        }
    },
    NORTH(Coordinate(0, 1)) {
        override fun rotate(dx : Int, dy: Int): Coordinate {
            return Coordinate(dx, dy)
        }
    },
    NORTH_EAST(Coordinate(1, 1)) {
        override fun rotate(dx : Int, dy: Int): Coordinate {
            return Coordinate(dx, dy)
        }
    },
    EAST(Coordinate(1, 0)) {
        override fun rotate(dx : Int, dy: Int): Coordinate {
            return Coordinate(-dy, dx)
        }
    },
    SOUTH_EAST(Coordinate(1, -1)) {
        override fun rotate(dx : Int, dy: Int): Coordinate {
            return Coordinate(dx, dy)
        }
    },
    SOUTH(Coordinate(0, -1)) {
        override fun rotate(dx : Int, dy: Int): Coordinate {
            return Coordinate(-dx, -dy)
        }
    },
    SOUTH_WEST(Coordinate(-1, -1)) {
        override fun rotate(dx : Int, dy: Int): Coordinate {
            return Coordinate(dx, dy)
        }
    },
    WEST(Coordinate(-1, 0)) {
        override fun rotate(dx : Int, dy: Int): Coordinate {
            return Coordinate(dy, -dx)
        }
    };

    abstract fun rotate(dx : Int, dy: Int): Coordinate
}
