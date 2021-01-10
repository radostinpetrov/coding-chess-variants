package pieces.hex

import boards.BoardHex
import coordinates.Coordinate2D
import moveGenerators.MoveGeneratorHex
import pieces.Piece

interface PieceHex : Piece<BoardHex, MoveGeneratorHex, PieceHex, Coordinate2D>
