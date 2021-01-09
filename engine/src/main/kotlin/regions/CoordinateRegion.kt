package regions

import coordinates.Coordinate2D

/**
 * Represents a region of a coordinate on 2D coordinate
 *
 * @property x x component of the coordinate
 * @property y y component of the coordinate
 */
data class CoordinateRegion(private val x: Int, private val y: Int) : Region2D {
    override fun isInRegion(coordinate: Coordinate2D): Boolean {
        return x == coordinate.x && y == coordinate.y
    }
}
