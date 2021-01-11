package gameTypes.chess

import boards.Board2D
import coordinates.Coordinate2D
import endconditions.EndCondition2D
import endconditions.StandardEndConditions
import gameTypes.AbstractChess
import gameTypes.GameType2D
import moveGenerators.MoveGenerator2D
import pieces.Piece2D
import rules.SpecialRules2D

/**
 * Represents a standard 2-player game on a 2D square board,
 * which contains no special rules and standard end conditions.
 *
 * @property rules the list of special rules (e.g. Castling, EnPassant)
 * @property endConditions the list of conditions that will end the game
 */
abstract class AbstractChess2D(
    rules: List<SpecialRules2D<AbstractChess2D>> = listOf(),
    endConditions: List<EndCondition2D<AbstractChess2D>> = listOf(StandardEndConditions()),
    startPlayer: Int = 0
) :
    GameType2D,
    AbstractChess<Board2D, MoveGenerator2D, Piece2D, Coordinate2D>(rules, endConditions, startPlayer)
