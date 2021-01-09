package boards

import coordinates.Coordinate
import moves.Move
import moveGenerators.MoveGenerator
import pieces.Piece
import players.Player

/**
 * Represents chess board
 *
 * @param B the type of a board.
 * @param MG the type of a move generator.
 * @param M the type of a move.
 * @param P the type of a piece.
 * @param C the type of a coordinate.
 */
interface Board<B : Board<B, MG, M, P, C>, MG : MoveGenerator<B, MG, M, P, C>, M: Move<B, MG, M, P, C>, P: Piece<B, MG, M, P, C>, C: Coordinate> {
    /**
     * Returns the current status of the board in terms of board
     * @return a map of coordinate and piece
     */
    fun getBoardState(): Map<C, P?>

    /**
     * Adds a piece at given coordinate onto the board.
     */
    fun addPiece(coordinate: C, piece: P)

    /**
     * Removes a piece at given coordinate from the board.
     */
    fun removePiece(coordinate: C, piece: P)

    /**
     * Returns a list of pairs of all pieces and the corresponding coordinate
     * that the piece is placed on
     *
     * @return a list of pairs representing which coordinate each piece is on
     */
    fun getPieces(): List<Pair<P, C>>

    /**
     * Returns a list of pairs of all pieces that a given player owns
     * and the corresponding coordinate that the piece is placed on
     *
     * @return a list of pairs representing which coordinate each piece is on
     * for the given player
     */
    fun getPieces(player: Player): List<Pair<P, C>>

    /**
     * Returns the piece placed on a given coordinate by reference
     * @return the piece placed on a given coordinate by reference
     */
    fun getPiece(coordinate: C): P?

    /**
     * Returns the coordinate of a given piece by reference
     * @return the coordinate of a given piece by reference
     */
    fun getPieceCoordinate(piece: P): C?

    /**
     * Reinitialise the board by
     * assigning null to all valid coordinate of the board
     */
    fun clearBoard()
}
