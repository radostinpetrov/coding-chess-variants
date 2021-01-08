package pieces.janggi

import coordinates.Coordinate2D
import regions.BoxRegion
import regions.CompositeRegion

/**
 * Contains special regions used in Janggi
 */
object SpecialRegion {
    val palace = CompositeRegion(listOf(
        BoxRegion(Coordinate2D(3, 0), Coordinate2D(5, 2)),
        BoxRegion(Coordinate2D(3, 7), Coordinate2D(5, 9))
    ))
}