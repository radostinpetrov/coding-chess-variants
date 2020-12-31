package pieces

import boards.Board2D
import coordinates.Coordinate2D
import gameMoves.GameMove2D
import moves.Move2D

interface Piece2D : Piece<Board2D, Move2D, GameMove2D, Piece2D, Coordinate2D>
