package regions

import coordinates.Coordinate

/**
 * Represents a region on the board
 *
 * @param C the type of a coordinate.
 */
interface Region<C: Coordinate> {
    /**
     * @return true if the coordinate is within the region specified
     */
    fun isInRegion(coordinate: C): Boolean
}