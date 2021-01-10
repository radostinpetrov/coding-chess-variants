package moveGenerators

import coordinates.Coordinate3D

/**
 * A collection of constants representing directions
 *
 * @property coordinate the vector corresponding to each direction
 */
enum class Direction3D(val coordinate: Coordinate3D) {
    NORTH_WEST(Coordinate3D(-1, 1, 0)),
    NORTH(Coordinate3D(0, 1, 0)),
    NORTH_EAST(Coordinate3D(1, 1, 0)),
    EAST(Coordinate3D(1, 0, 0)),
    SOUTH_EAST(Coordinate3D(1, -1, 0)),
    SOUTH(Coordinate3D(0, -1, 0)),
    SOUTH_WEST(Coordinate3D(-1, -1, 0)),
    WEST(Coordinate3D(-1, 0, 0)),
    ZENITH(Coordinate3D(0, 0, 1)),
    ZENITH_NORTH(Coordinate3D(0, 1, 1)),
    ZENITH_WEST(Coordinate3D(-1, 0, 1)),
    ZENITH_EAST(Coordinate3D(1, 0, 1)),
    NADIR(Coordinate3D(0, 0, -1)),
    NADIR_SOUTH(Coordinate3D(0, -1, -1)),
    NADIR_WEST(Coordinate3D(-1, 0, -1)),
    NADIR_EAST(Coordinate3D(1, 0, -1))
}
