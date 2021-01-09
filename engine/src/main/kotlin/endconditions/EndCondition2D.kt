package endconditions

import boards.Board2D
import coordinates.Coordinate2D
import gameTypes.GameType2D
import moveGenerators.MoveGenerator2D
import moves.Move2D
import pieces.Piece2D

/**
 * Implementation of the EndCondition interface for a 2D square board.
 */
interface EndCondition2D<G : GameType2D> : EndCondition<G, Board2D, MoveGenerator2D, Move2D, Piece2D, Coordinate2D>
