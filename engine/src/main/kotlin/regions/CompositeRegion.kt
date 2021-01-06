package regions

import coordinates.Coordinate2D

data class CompositeRegion(val regions: List<Region2D>) : Region2D {
    override fun isInRegion(coordinate: Coordinate2D): Boolean {
        return regions.any { it.isInRegion(coordinate) }
    }
}
