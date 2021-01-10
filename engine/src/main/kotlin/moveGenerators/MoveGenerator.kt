package moveGenerators

import boards.Board
import coordinates.Coordinate
import moves.Move
import pieces.Piece
import players.Player

/**
 * Represents a type of the movement of pieces.
 *
 * @param B the type of a board.
 * @param MG the type of a move generator.
 * @param M the type of a move.
 * @param P the type of a piece.
 * @param C the type of a coordinate.
 */
interface MoveGenerator<B : Board<B, MG, P, C>, MG : MoveGenerator<B, MG, P, C>, P : Piece<B, MG, P, C>, C : Coordinate> {
    /**
     * Takes in the current board, the piece to be moved and its coordinate, and
     * the player who makes the move to return all possible game moves
     * corresponding to the given type of movement.
     *
     * @param board the current board
     * @param coordinate the coordinate of the piece to be moved
     * @param piece the piece to be moved
     * @param player the player who makes the move
     * @return a list of corresponding game moves.
     */
    fun generate(board: B, coordinate: C, piece: P, player: Player): List<M>
}
