package moves.region

import coordinates.Coordinate2D

class BoxRegion(private val corner1: Coordinate2D, private val corner2: Coordinate2D) : Region {
    override fun isInRegion(coordinate: Coordinate2D): Boolean {
        return coordinate.x >= minOf(corner1.x, corner2.x) &&
            coordinate.x <= maxOf(corner1.x, corner2.x) &&
            coordinate.y >= minOf(corner1.y, corner2.y) &&
            coordinate.y <= maxOf(corner1.y, corner2.y)
    }
}
