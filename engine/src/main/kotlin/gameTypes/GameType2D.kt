package gameTypes

import gameMoves.GameMove2D
import boards.Board2D
import coordinates.Coordinate2D
import moves.Move2D
import pieces.Piece2D

interface GameType2D : GameType<Board2D, Move2D, GameMove2D, Piece2D, Coordinate2D>
