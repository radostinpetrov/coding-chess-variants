package moves

import Coordinate

enum class Direction(val coordinate: Coordinate) {
    NORTH(Coordinate(0, 1)) {
        override fun rotate(dx: Int, dy: Int): Coordinate {
            return Coordinate(dx, dy)
        }
    },
    EAST(Coordinate(1, 0)) {
        override fun rotate(dx: Int, dy: Int): Coordinate {
            return Coordinate(-dy, dx)
        }
    },
    SOUTH(Coordinate(0, -1)) {
        override fun rotate(dx: Int, dy: Int): Coordinate {
            return Coordinate(-dx, -dy)
        }
    },
    WEST(Coordinate(-1, 0)) {
        override fun rotate(dx: Int, dy: Int): Coordinate {
            return Coordinate(dy, -dx)
        }
    };

    abstract fun rotate(dx: Int, dy: Int): Coordinate
}

// sealed class in move instead of
