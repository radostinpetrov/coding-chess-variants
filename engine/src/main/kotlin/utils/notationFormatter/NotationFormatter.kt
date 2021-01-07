package utils.notationFormatter

import coordinates.Coordinate2D
import moves.Move2D

interface NotationFormatter {
    /* Converts the string representation of a coordinate to a coordinate. */
    fun strToCoordinate(s: String): Coordinate2D?

    /* Converts a coordinate to the string representation of a coordinate. */
    fun coordinateToStr(c: Coordinate2D): String

    /* Get the string representation of a game move. */
    fun moveToStr(move: Move2D): String
}