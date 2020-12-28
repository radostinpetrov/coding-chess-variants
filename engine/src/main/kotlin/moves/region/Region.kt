package moves.region

import Coordinate

interface Region {
    fun isInRegion(coordinate: Coordinate): Boolean
}
