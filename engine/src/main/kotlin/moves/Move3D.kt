package moves

import boards.Board3D
import coordinates.Coordinate3D
import moveGenerators.MoveGenerator3D
import pieces.Piece3D

typealias Move3D = Move<Board3D, MoveGenerator3D, Piece3D, Coordinate3D>
typealias SimpleMove3D = Move.SimpleMove<Board3D, MoveGenerator3D, Piece3D, Coordinate3D>
typealias BasicMove3D = Move.SimpleMove.BasicMove<Board3D, MoveGenerator3D, Piece3D, Coordinate3D>
typealias AddPieceMove3D = Move.SimpleMove.AddPieceMove<Board3D, MoveGenerator3D, Piece3D, Coordinate3D>
typealias RemovePieceMove3D = Move.SimpleMove.RemovePieceMove<Board3D, MoveGenerator3D, Piece3D, Coordinate3D>
typealias CompositeMove3D = Move.CompositeMove<Board3D, MoveGenerator3D, Piece3D, Coordinate3D>