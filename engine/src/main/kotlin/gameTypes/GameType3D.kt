package gameTypes

import moves.Move2D
import boards.Board2D
import boards.Board3D
import coordinates.Coordinate2D
import coordinates.Coordinate3D
import moveGenerators.MoveGenerator2D
import moveGenerators.MoveGenerator3D
import pieces.Piece2D
import pieces.Piece3D

/**
 * Implementation of the GameType interface for a standard 2d square board.
 */
interface GameType3D : GameType<Board3D, MoveGenerator3D, Piece3D, Coordinate3D>
