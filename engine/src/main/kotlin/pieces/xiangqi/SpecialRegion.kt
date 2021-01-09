package pieces.xiangqi

import coordinates.Coordinate2D
import regions.BoxRegion
import regions.CompositeRegion

/**
 * Contains special regions used in Xiangqi
 */
object SpecialRegion {
    val palace = CompositeRegion(listOf(
        BoxRegion(Coordinate2D(3, 0), Coordinate2D(5, 2)),
        BoxRegion(Coordinate2D(3, 7), Coordinate2D(5, 9))
    ))

    val blueBeforeRiver = BoxRegion(Coordinate2D(0, 5), Coordinate2D(8, 9))
    val blueSideRiver = BoxRegion(Coordinate2D(0, 5), Coordinate2D(9, 9))

    val redBeforeRiver = BoxRegion(Coordinate2D(0, 0), Coordinate2D(8, 4))
    val redSideRiver = BoxRegion(Coordinate2D(0, 0), Coordinate2D(9, 4))
}