package moves

import pieces.Piece

sealed class Move {
    data class Slider(val H: Boolean = false, val V: Boolean = false, val D: Boolean = false, val A: Boolean = false) : Move()
    data class Stepper(val direction: Direction, val step: Int, val canCapture: Boolean = false) : Move()
    data class Leaper(val dx: Int, val dy: Int) : Move()
    data class Hopper(val HV: Boolean = false, val D: Boolean = false, val canJumpOverSamePiece: Boolean) : Move()
    data class CaptureOnly(val move: Move) : Move()
    data class NoCapture(val move: Move): Move()
    data class Restricted(val move: Move, val x: List<Int>, val y: List<Int>) : Move()
    data class RestrictedDestination(val move: Move, val x: List<Int>, val y: List<Int>) : Move()
    data class AddForcedPromotion(val move: Move, val x: List<Int>, val y: List<Int>, val promoPieces: List<Piece>) : Move()
    data class Composite(val moves: List<Move>) : Move()
}
