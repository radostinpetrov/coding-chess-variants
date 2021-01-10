package rules.hex

import boards.BoardHex
import coordinates.Coordinate2D
import gameTypes.hex.GameTypeHex
import moveGenerators.MoveGeneratorHex
import pieces.hex.PieceHex
import rules.SpecialRules

/**
 * Implementation of the SpecialRule interface for a 2d square board.
 */
interface SpecialRulesHex<out G : GameTypeHex> : SpecialRules<G, BoardHex, MoveGeneratorHex, PieceHex, Coordinate2D>
