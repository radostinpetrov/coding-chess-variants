package moveGenerators

import coordinates.Coordinate2D

/**
 * A collection of constants representing directions in 2 dimensions
 *
 * @property coordinate the vector corresponding to each direction
 */
enum class Direction(val coordinate: Coordinate2D) {
    NORTH_WEST(Coordinate2D(-1, 1)) {
        override fun rotate(dx: Int, dy: Int): Coordinate2D {
            return Coordinate2D(dx, dy)
        }
    },
    NORTH(Coordinate2D(0, 1)) {
        override fun rotate(dx: Int, dy: Int): Coordinate2D {
            return Coordinate2D(dx, dy)
        }
    },
    NORTH_EAST(Coordinate2D(1, 1)) {
        override fun rotate(dx: Int, dy: Int): Coordinate2D {
            return Coordinate2D(dx, dy)
        }
    },
    EAST(Coordinate2D(1, 0)) {
        override fun rotate(dx: Int, dy: Int): Coordinate2D {
            return Coordinate2D(-dy, dx)
        }
    },
    SOUTH_EAST(Coordinate2D(1, -1)) {
        override fun rotate(dx: Int, dy: Int): Coordinate2D {
            return Coordinate2D(dx, dy)
        }
    },
    SOUTH(Coordinate2D(0, -1)) {
        override fun rotate(dx: Int, dy: Int): Coordinate2D {
            return Coordinate2D(-dx, -dy)
        }
    },
    SOUTH_WEST(Coordinate2D(-1, -1)) {
        override fun rotate(dx: Int, dy: Int): Coordinate2D {
            return Coordinate2D(dx, dy)
        }
    },
    WEST(Coordinate2D(-1, 0)) {
        override fun rotate(dx: Int, dy: Int): Coordinate2D {
            return Coordinate2D(dy, -dx)
        }
    };

    abstract fun rotate(dx: Int, dy: Int): Coordinate2D
}
