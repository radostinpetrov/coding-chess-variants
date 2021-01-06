package regions

import coordinates.Coordinate2D

data class ColumnRegion(val col: Int) : Region2D {
    override fun isInRegion(coordinate: Coordinate2D): Boolean {
        return coordinate.x == col
    }
}