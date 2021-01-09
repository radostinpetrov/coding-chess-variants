package regions

import coordinates.Coordinate2D

/**
 * Represents a region made up of a list of regions
 *
 * @property regions a list of regions
 */
data class CompositeRegion(val regions: List<Region2D>) : Region2D {
    override fun isInRegion(coordinate: Coordinate2D): Boolean {
        return regions.any { it.isInRegion(coordinate) }
    }
}
