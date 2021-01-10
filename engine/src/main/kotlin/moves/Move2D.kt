package moves

import boards.Board2D
import coordinates.Coordinate2D
import moveGenerators.MoveGenerator2D
import pieces.Piece2D

typealias Move2D = Move<Board2D, MoveGenerator2D, Piece2D, Coordinate2D>
typealias SimpleMove2D = Move.SimpleMove<Board2D, MoveGenerator2D, Piece2D, Coordinate2D>
typealias BasicMove2D = Move.SimpleMove.BasicMove<Board2D, MoveGenerator2D, Piece2D, Coordinate2D>
typealias AddPieceMove2D = Move.SimpleMove.AddPieceMove<Board2D, MoveGenerator2D, Piece2D, Coordinate2D>
typealias RemovePieceMove2D = Move.SimpleMove.RemovePieceMove<Board2D, MoveGenerator2D, Piece2D, Coordinate2D>
typealias CompositeMove2D = Move.CompositeMove<Board2D, MoveGenerator2D, Piece2D, Coordinate2D>