package boards

import coordinates.Coordinate
import coordinates.Coordinate2D
import gameMoves.GameMove
import moves.Move
import pieces.Piece
import players.Player

interface Board<B : Board<B, M, GM, P, C>, M : Move<B, M, GM, P, C>, GM: GameMove<B, M, GM, P, C>, P: Piece<B, M, GM, P, C>, C: Coordinate> {
    fun getBoardState(): Array<Array<P?>>
    fun addPiece(coordinate: C, piece: P)
    fun removePiece(coordinate: C, piece: P)
    fun getPieces(): List<Pair<P, C>>
    fun getPieces(player: Player): List<Pair<P, C>>
    fun getPiece(coordinate: C): P?
    fun getPieceCoordinate(piece: P): C?
}
