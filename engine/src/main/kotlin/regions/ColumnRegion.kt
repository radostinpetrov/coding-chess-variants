package regions

import coordinates.Coordinate2D

/**
 * Represents a region of a column on 2D coordinate
 *
 * @property col the column number (x coordinate)
 */
data class ColumnRegion(val col: Int) : Region2D {
    override fun isInRegion(coordinate: Coordinate2D): Boolean {
        return coordinate.x == col
    }
}