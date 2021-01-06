package regions

import coordinates.Coordinate2D

data class RowRegion(val row: Int) : Region2D {
    override fun isInRegion(coordinate: Coordinate2D): Boolean {
        return coordinate.y == row
    }
}
