package pieces

import boards.Board2D
import coordinates.Coordinate2D
import gameMoves.GameMove2D.SimpleGameMove.BasicGameMove
import moves.Move2D

interface Piece2D : Piece<Board2D, Move2D, BasicGameMove, Piece2D, Coordinate2D>