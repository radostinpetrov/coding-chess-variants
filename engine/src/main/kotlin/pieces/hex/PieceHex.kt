package pieces.hex

import boards.BoardHex
import coordinates.Coordinate2D
import moveGenerators.MoveGeneratorHex
import pieces.Piece

/**
 * Implementation of the Piece interface for pieces on a 2d hexagonal board
 */
interface PieceHex : Piece<BoardHex, MoveGeneratorHex, PieceHex, Coordinate2D>
