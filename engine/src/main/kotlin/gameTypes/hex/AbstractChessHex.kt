package gameTypes.hex

import boards.BoardHex
import coordinates.Coordinate2D
import endconditions.hex.EndConditionHex
import endconditions.hex.StandardEndConditionsHex
import gameTypes.AbstractChess
import moveGenerators.MoveGeneratorHex
import pieces.hex.PieceHex
import rules.hex.SpecialRulesHex

/**
 * Represents a standard 2-player game on a 2D hexagonal board,
 * which contains no special rules and standard end conditions.
 *
 * @property rules the list of special rules (e.g. Castling, EnPassant)
 * @property endConditions the list of conditions that will end the game
 */
abstract class AbstractChessHex(
    rules: List<SpecialRulesHex<AbstractChessHex>> = listOf(),
    endConditions: List<EndConditionHex<AbstractChessHex>> = listOf(StandardEndConditionsHex()),
    startPlayer: Int = 0):
    GameTypeHex,
    AbstractChess<BoardHex, MoveGeneratorHex, PieceHex, Coordinate2D>(rules, endConditions, startPlayer)

