package coordinates

data class Coordinate2D(val x: Int, val y: Int) : Coordinate {
    override fun toString(): String {
        return ("($x, $y)")
    }
}
