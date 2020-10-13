package pieces

import moves.Move

interface Piece {
    val move: List<Move>
    fun getSymbol(): String
}