package pieces

import moves.Move

interface Piece {
    fun possibleMoves(): List<Move>
}