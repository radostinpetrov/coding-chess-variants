package regions

import coordinates.Coordinate2D

data class CoordinateRegion(private val x: Int, private val y: Int) : Region2D {
    override fun isInRegion(coordinate: Coordinate2D): Boolean {
        return x == coordinate.x && y == coordinate.y
    }
}
