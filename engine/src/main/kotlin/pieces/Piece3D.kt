package pieces

import boards.Board3D
import coordinates.Coordinate3D
import moveGenerators.MoveGenerator3D

interface Piece3D : Piece<Board3D, MoveGenerator3D, Piece3D, Coordinate3D>