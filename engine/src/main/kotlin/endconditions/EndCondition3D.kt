package endconditions

import boards.Board2D
import boards.Board3D
import coordinates.Coordinate2D
import coordinates.Coordinate3D
import gameTypes.GameType2D
import gameTypes.GameType3D
import moveGenerators.MoveGenerator2D
import moveGenerators.MoveGenerator3D
import moves.Move2D
import pieces.Piece2D
import pieces.Piece3D

/**
 * Implementation of the EndCondition interface for a 2D square board.
 */
interface EndCondition3D<out G : GameType3D> : EndCondition<G, Board3D, MoveGenerator3D, Piece3D, Coordinate3D>
