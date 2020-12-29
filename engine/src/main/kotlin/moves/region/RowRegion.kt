package moves.region

import coordinates.Coordinate2D

class RowRegion(val row: Int) : Region {
    override fun isInRegion(coordinate: Coordinate2D): Boolean {
        return coordinate.y == row
    }
}
