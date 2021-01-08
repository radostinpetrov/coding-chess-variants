package rules

import boards.Board2D
import coordinates.Coordinate2D
import moves.Move2D
import gameTypes.GameType2D
import moveGenerators.MoveGenerator2D
import pieces.Piece2D

/**
 * Implementation of the SpecialRule interface for a 2d square board.
 */
interface SpecialRules2D<out G : GameType2D> : SpecialRules<G, Board2D, MoveGenerator2D, Move2D, Piece2D, Coordinate2D>
