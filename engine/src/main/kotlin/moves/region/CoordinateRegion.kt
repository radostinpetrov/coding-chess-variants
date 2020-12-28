package moves.region

import Coordinate

class CoordinateRegion(private val x: Int, private val y: Int) : Region {
    override fun isInRegion(coordinate: Coordinate): Boolean {
        return x == coordinate.x && y == coordinate.y
    }
}
