package pieces

import boards.Board
import coordinates.Coordinate
import moves.Move
import moveGenerators.MoveGenerator
import players.Player

/**
 * Represents chess board
 *
 * @param B the type of a board.
 * @param MG the type of a move generator.
 * @param M the type of a move.
 * @param P the type of a piece.
 * @param C the type of a coordinate.
 *
 * @property moveGenerators that corresponds to the piece
 * @property player the player that owns the piece
 */
interface Piece<B : Board<B, MG, M, P, C>, MG : MoveGenerator<B, MG, M, P, C>, M: Move<B, MG, M, P, C>, P: Piece<B, MG, M, P, C>, C: Coordinate> {
    val moveGenerators: List<MG>
    val player: Player

    /**
     * @return a unique symbol of the piece for the console game
     */
    fun getSymbol(): String
}