package gameTypes

import boards.Board3D
import coordinates.Coordinate3D
import moveGenerators.MoveGenerator3D
import pieces.Piece3D

/**
 * Implementation of the GameType interface for a standard 3D square board.
 */
interface GameType3D : GameType<Board3D, MoveGenerator3D, Piece3D, Coordinate3D>
