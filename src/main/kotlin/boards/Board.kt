package boards

import Coordinate
import GameMove
import pieces.Piece

interface Board {
    fun getBoardState(): Array<Array<Piece>>

    fun getPieces(): List<Pair<Piece, Coordinate>>
    fun getPiece(coordinate: Coordinate): Piece
    fun getPieceCoordinate(piece: Piece): Coordinate
}
