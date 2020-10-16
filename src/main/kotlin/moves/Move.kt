package moves

sealed class Move {
    data class Slider(val H: Boolean = false, val V: Boolean = false, val D: Boolean = false, val A: Boolean = false): Move()
    data class Stepper(val direction: Direction, val step: Int, val canCapture: Boolean = false): Move()
    data class Leaper(val dx: Int, val dy: Int): Move()
    data class CaptureOnly(val move: Move): Move()
    data class Restricted(val move: Move, val x: List<Int>, val y: List<Int>): Move()
}

//sealed class
