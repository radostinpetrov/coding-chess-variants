package boards

import coordinates.Coordinate
import moves.Move
import moveGenerators.MoveGenerator
import pieces.Piece
import players.Player

interface Board<B : Board<B, MG, M, P, C>, MG : MoveGenerator<B, MG, M, P, C>, M: Move<B, MG, M, P, C>, P: Piece<B, MG, M, P, C>, C: Coordinate> {
    fun getBoardState(): Map<C, P?>
    fun addPiece(coordinate: C, piece: P)
    fun removePiece(coordinate: C, piece: P)
    fun getPieces(): List<Pair<P, C>>
    fun getPieces(player: Player): List<Pair<P, C>>
    fun getPiece(coordinate: C): P?
    fun getPieceCoordinate(piece: P): C?
    fun clearBoard()
}
