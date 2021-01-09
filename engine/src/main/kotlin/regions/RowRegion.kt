package regions

import coordinates.Coordinate2D

/**
 * Represents a region of a row on 2D coordinate
 *
 * @property row the row number (y coordinate)
 */
data class RowRegion(val row: Int) : Region2D {
    override fun isInRegion(coordinate: Coordinate2D): Boolean {
        return coordinate.y == row
    }
}
