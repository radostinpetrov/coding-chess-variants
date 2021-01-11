package regions

import coordinates.Coordinate3D

/**
 * Represents a box region on 3D coordinate
 *
 * @property corner1 one corner of the box
 * @property corner2 the opposite corner of the box
 */

data class BoxRegion3D(private val corner1: Coordinate3D, private val corner2: Coordinate3D) : Region3D {
    override fun isInRegion(coordinate: Coordinate3D): Boolean {
        return coordinate.x >= minOf(corner1.x, corner2.x) &&
            coordinate.x <= maxOf(corner1.x, corner2.x) &&
            coordinate.y >= minOf(corner1.y, corner2.y) &&
            coordinate.y <= maxOf(corner1.y, corner2.y) &&
            coordinate.z >= minOf(corner1.z, corner2.z) &&
            coordinate.z <= maxOf(corner1.z, corner2.z)
    }
}
