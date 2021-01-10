package pieces

import boards.Board2D
import coordinates.Coordinate2D
import moves.Move
import moveGenerators.MoveGenerator2D

/**
 * Implementation of the Piece interface for pieces on a standard 2d square board
 */
interface Piece2D : Piece<Board2D, MoveGenerator2D, Move, Piece2D, Coordinate2D>
