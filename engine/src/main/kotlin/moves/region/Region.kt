package moves.region

import coordinates.Coordinate2D

interface Region {
    fun isInRegion(coordinate: Coordinate2D): Boolean
}
