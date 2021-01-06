package regions

import coordinates.Coordinate

interface Region<C: Coordinate> {
    fun isInRegion(coordinate: C): Boolean
}