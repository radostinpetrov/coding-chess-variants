interface NotationFormatter {
    fun strToCoordinate(s: String): Coordinate?
    fun coordinateToStr(c: Coordinate): String?
    fun gameMoveToStr(gameMove: GameMove): String
}