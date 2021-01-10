package moves

import boards.BoardHex
import coordinates.Coordinate2D
import moveGenerators.MoveGeneratorHex
import pieces.hex.PieceHex

typealias MoveHex = Move<BoardHex, MoveGeneratorHex, PieceHex, Coordinate2D>
typealias SimpleMoveHex = Move.SimpleMove<BoardHex, MoveGeneratorHex, PieceHex, Coordinate2D>
typealias BasicMoveHex = Move.SimpleMove.BasicMove<BoardHex, MoveGeneratorHex, PieceHex, Coordinate2D>
typealias AddPieceMoveHex = Move.SimpleMove.AddPieceMove<BoardHex, MoveGeneratorHex, PieceHex, Coordinate2D>
typealias RemovePieceMoveHex = Move.SimpleMove.RemovePieceMove<BoardHex, MoveGeneratorHex, PieceHex, Coordinate2D>
typealias CompositeMoveHex = Move.CompositeMove<BoardHex, MoveGeneratorHex, PieceHex, Coordinate2D>