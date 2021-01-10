package moveGenerators

import coordinates.Coordinate2D

/**
 * A collection of constants representing directions
 *
 * @property coordinate the vector corresponding to each direction
 */
enum class DirectionHex(val coordinate: Coordinate2D) {
    UP(Coordinate2D(0, 2)),
    UP_UP_RIGHT(Coordinate2D(1, 3)),
    UP_RIGHT(Coordinate2D(1, 1)),
    RIGHT(Coordinate2D(2, 0)),
    DOWN_RIGHT(Coordinate2D(1, -1)),
    DOWN_DOWN_RIGHT(Coordinate2D(1, -3)),
    DOWN(Coordinate2D(0, -2)),
    DOWN_DOWN_LEFT(Coordinate2D(-1, -3)),
    DOWN_LEFT(Coordinate2D(-1, -1)),
    LEFT(Coordinate2D(-2, 0)),
    UP_LEFT(Coordinate2D(-1, 1)),
    UP_UP_LEFT(Coordinate2D(-1, 3))
}
