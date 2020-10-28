package main.kotlin

interface NotationFormatter {
    /* Converts the string representation of a coordinate to a coordinate. */
    fun strToCoordinate(s: String): Coordinate?

    /* Converts a coordinate to the string representation of a coordinate. */
    fun coordinateToStr(c: Coordinate): String?

    /* Get the string representation of a game move. */
    fun gameMoveToStr(gameMove: GameMove): String
}
