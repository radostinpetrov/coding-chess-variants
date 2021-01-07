package coordinates

data class Coordinate2D(val x: Int, val y: Int) : Coordinate {
    operator fun plus(other: Coordinate2D): Coordinate2D {
        return Coordinate2D(x + other.x, y + other.y)
    }
    operator fun div(denom: Int): Coordinate2D {
        return Coordinate2D(x / denom, y / denom)
    }
    override fun toString(): String {
        return ("($x, $y)")
    }
}
