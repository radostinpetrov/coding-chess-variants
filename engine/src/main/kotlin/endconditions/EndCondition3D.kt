package endconditions

import boards.Board3D
import coordinates.Coordinate3D
import gameTypes.GameType3D
import moveGenerators.MoveGenerator3D
import pieces.Piece3D

/**
 * Implementation of the EndCondition interface for a 3D square board.
 */
interface EndCondition3D<out G : GameType3D> : EndCondition<G, Board3D, MoveGenerator3D, Piece3D, Coordinate3D>
