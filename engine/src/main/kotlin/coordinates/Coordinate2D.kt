package coordinates

/**
 * Coordinate on a 2D board representing
 * the x,y offset from the bottom left of the board
 *
 * @property x the x offset from the bottom left of the board
 * @property y the y offset from the bottom left of the board
 */
data class Coordinate2D(val x: Int, val y: Int) : Coordinate {
    /**
     * Returns the sum of the current coordinate and a given coordinate,
     * which is achieved by element-wise addition
     *
     * @return the sum of the current coordinate and a given coordinate
     */
    operator fun plus(other: Coordinate2D): Coordinate2D {
        return Coordinate2D(x + other.x, y + other.y)
    }

    /**
     * Returns the current coordinate divided by a given denominator,
     * which is achieved by dividing (integer division) each component of
     * the current coordinate by the given denominator
     *
     * @return the current coordinate divided by a given denominator
     */
    operator fun div(denom: Int): Coordinate2D {
        return Coordinate2D(x / denom, y / denom)
    }
    override fun toString(): String {
        return ("($x, $y)")
    }
}
