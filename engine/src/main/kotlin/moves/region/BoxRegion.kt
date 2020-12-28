package moves.region

import Coordinate

class BoxRegion(private val corner1: Coordinate, private val corner2: Coordinate) : Region {
    override fun isInRegion(coordinate: Coordinate): Boolean {
        return coordinate.x >= minOf(corner1.x, corner2.x) &&
            coordinate.x <= maxOf(corner1.x, corner2.x) &&
            coordinate.y >= minOf(corner1.y, corner2.y) &&
            coordinate.y <= maxOf(corner1.y, corner2.y)
    }
}
