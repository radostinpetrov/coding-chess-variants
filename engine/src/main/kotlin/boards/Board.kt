package boards

import Coordinate
import pieces.Piece
import players.Player

interface Board <T : Piece> {
    fun getBoardState(): Array<Array<T?>>
    fun addPiece(coordinate: Coordinate, piece: T)
    fun removePiece(coordinate: Coordinate, piece: T)
    fun getPieces(): List<Pair<T, Coordinate>>
    fun getPieces(player: Player): List<Pair<T, Coordinate>>
    fun getPiece(coordinate: Coordinate): T?
    fun getPieceCoordinate(piece: T): Coordinate?
}