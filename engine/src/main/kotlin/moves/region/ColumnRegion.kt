package moves.region

import Coordinate

class ColumnRegion(val col: Int) : Region {
    override fun isInRegion(coordinate: Coordinate): Boolean {
        return coordinate.x == col
    }
}