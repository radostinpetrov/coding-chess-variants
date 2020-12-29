package moves.region

import coordinates.Coordinate2D

class CompositeRegion(val regions: List<Region>) : Region {
    override fun isInRegion(coordinate: Coordinate2D): Boolean {
        return regions.any { it.isInRegion(coordinate) }
    }
}
