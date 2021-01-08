package moves

import boards.Board
import coordinates.Coordinate
import moveGenerators.MoveGenerator
import pieces.Piece

/**
 * Represents a type of the movement of pieces.
 *
 * @param B the type of a board.
 * @param MG the type of a move generator.
 * @param M the type of a move.
 * @param P the type of a piece.
 * @param C the type of a coordinate.
 */
interface Move<B : Board<B, MG, M, P, C>, MG : MoveGenerator<B, MG, M, P, C>, M : Move<B, MG, M, P, C>, P : Piece<B, MG, M, P, C>, C : Coordinate>
