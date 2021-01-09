package regions

import coordinates.Coordinate2D

/**
 * Represents a box region on 2D coordinate
 * @property corner1
 * @property corner2
 */
data class BoxRegion(private val corner1: Coordinate2D, private val corner2: Coordinate2D) : Region2D {
    override fun isInRegion(coordinate: Coordinate2D): Boolean {
        return coordinate.x >= minOf(corner1.x, corner2.x) &&
            coordinate.x <= maxOf(corner1.x, corner2.x) &&
            coordinate.y >= minOf(corner1.y, corner2.y) &&
            coordinate.y <= maxOf(corner1.y, corner2.y)
    }
}
