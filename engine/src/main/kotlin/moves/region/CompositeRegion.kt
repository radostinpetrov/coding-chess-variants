package moves.region

import Coordinate

class CompositeRegion(val regions: List<Region>) : Region {
    override fun isInRegion(coordinate: Coordinate): Boolean {
        return regions.any { it.isInRegion(coordinate) }
    }
}
