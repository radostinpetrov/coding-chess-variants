package utils.notationFormatter

import coordinates.Coordinate2D
import moves.Move

/**
 * Interface for a notation formatter to be used by front end console implementations.
 * Different notation formatters could be made for standard chess and asian chess variants.
 */
interface NotationFormatter {

    /**
     * Takes a string representation of a coordinate and converts to a coordinate.
     * @return Coordinate2D
     */
    fun strToCoordinate(s: String): Coordinate2D?

    /**
     * Takes a coordinate and converts to the string representation of a coordinate.
     * @return String coordinate
     */
    fun coordinateToStr(c: Coordinate2D): String

    /**
     * Takes a Move2D and converts to a readable representation
     * @return String move
     */
    fun moveToStr(move: Move): String
}
