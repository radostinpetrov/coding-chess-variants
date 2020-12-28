package moves.region

import Coordinate

class RowRegion(val row: Int) : Region {
    override fun isInRegion(coordinate: Coordinate): Boolean {
        return coordinate.y == row
    }
}
