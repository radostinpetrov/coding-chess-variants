package pieces

import moves.Move

interface Piece {
    fun getSymbol(): String
    fun getMove(): Move
}