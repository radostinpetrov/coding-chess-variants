package coordinates

/**
 * Coordinate on a 3D board representing
 * the x,y,z offset from the bottom left corner of the board
 *
 * @property x the x offset from the bottom left of the board
 * @property y the y offset from the bottom left of the board
 * @property z the z offset from the bottom left of the board
 */
data class Coordinate3D(val x: Int, val y: Int, val z: Int) : Coordinate {
    /**
     * Returns the sum of the current coordinate and a given coordinate,
     * which is achieved by element-wise addition
     *
     * @return the sum of the current coordinate and a given coordinate
     */
    operator fun plus(other: Coordinate3D): Coordinate3D {
        return Coordinate3D(x + other.x, y + other.y, z + other.z)
    }

    /**
     * Returns the current coordinate divided by a given denominator,
     * which is achieved by dividing (integer division) each component of
     * the current coordinate by the given denominator
     *
     * @return the current coordinate divided by a given denominator
     */
    operator fun div(denom: Int): Coordinate3D {
        return Coordinate3D(x / denom, y / denom, z / denom)
    }
    override fun toString(): String {
        return ("($x, $y, $z)")
    }
}
