package moves.region

import coordinates.Coordinate2D

class CoordinateRegion(private val x: Int, private val y: Int) : Region {
    override fun isInRegion(coordinate: Coordinate2D): Boolean {
        return x == coordinate.x && y == coordinate.y
    }
}
