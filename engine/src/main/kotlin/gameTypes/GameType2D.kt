package gameTypes

import moves.Move2D
import boards.Board2D
import coordinates.Coordinate2D
import moveGenerators.MoveGenerator2D
import pieces.Piece2D

interface GameType2D : GameType<Board2D, MoveGenerator2D, Move2D, Piece2D, Coordinate2D>
