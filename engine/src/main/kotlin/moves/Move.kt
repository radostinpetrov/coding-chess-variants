package main.kotlin.moves

import main.kotlin.pieces.Piece

sealed class Move {
    data class Slider(val H: Boolean = false, val V: Boolean = false, val D: Boolean = false, val A: Boolean = false) : Move()
    data class Stepper(val direction: Direction, val step: Int, val canCapture: Boolean = false) : Move()
    data class Leaper(val dx: Int, val dy: Int) : Move()
    object Hopper : Move()
    data class CaptureOnly(val move: Move) : Move()
    data class Restricted(val move: Move, val x: List<Int>, val y: List<Int>) : Move()
    data class AddForcedPromotion(val move: Move, val x: List<Int>, val y: List<Int>, val promoPieces: List<Piece>) : Move()
}
