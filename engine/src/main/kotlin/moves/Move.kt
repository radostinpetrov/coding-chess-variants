package moves

import moves.region.Region
import pieces.Piece

sealed class Move {
    data class Slider(val H: Boolean = false, val V: Boolean = false, val D: Boolean = false, val A: Boolean = false) : Move()
    data class Stepper(val direction: Direction, val step: Int, val canCapture: Boolean = false) : Move()
    data class Leaper(val dx: Int, val dy: Int) : Move()
    data class Hopper(val HV: Boolean = false, val D: Boolean = false, val canJumpOverSamePiece: Boolean) : Move()
    data class CaptureOnly(val move: Move) : Move()
    data class NoCapture(val move: Move) : Move()
    data class Restricted(val move: Move, val region: Region) : Move()
    data class RestrictedDestination(val move: Move, val region: Region) : Move()
    data class AddPromotion(val move: Move, val region: Region, val promoPieces: List<Piece>, val forced: Boolean) : Move()
    data class Composite(val moves: List<Move>) : Move()
}
