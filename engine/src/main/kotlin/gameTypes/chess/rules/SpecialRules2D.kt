package gameTypes.chess.rules

import boards.Board2D
import coordinates.Coordinate2D
import gameMoves.GameMove2D
import gameTypes.GameType2D
import gameTypes.SpecialRules
import moves.Move2D
import pieces.Piece2D

interface SpecialRules2D<out G : GameType2D> : SpecialRules<G, Board2D, Move2D, GameMove2D, Piece2D, Coordinate2D>
