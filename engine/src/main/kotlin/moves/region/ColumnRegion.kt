package moves.region

import coordinates.Coordinate2D

class ColumnRegion(val col: Int) : Region {
    override fun isInRegion(coordinate: Coordinate2D): Boolean {
        return coordinate.x == col
    }
}