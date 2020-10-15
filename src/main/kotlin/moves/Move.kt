package moves

sealed class Move {
    data class Slider(val H: Boolean = false, val V: Boolean = false, val D: Boolean = false, val A: Boolean = false): Move()
    data class Stepper(val direction: Direction, val step: Int): Move()
    data class Leaper(val dx: Int, val dy: Int): Move()
}

//sealed class
