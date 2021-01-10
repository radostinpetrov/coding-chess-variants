package endconditions.hex

import boards.BoardHex
import coordinates.Coordinate2D
import endconditions.EndCondition
import gameTypes.hex.GameTypeHex
import moveGenerators.MoveGeneratorHex
import pieces.hex.PieceHex

/**
 * Implementation of the EndCondition interface for a Hex square board.
 */
interface EndConditionHex<out G : GameTypeHex> : EndCondition<G, BoardHex, MoveGeneratorHex, PieceHex, Coordinate2D>
