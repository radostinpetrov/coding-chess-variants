package gameTypes.chess3d

import boards.Board3D
import coordinates.Coordinate3D
import endconditions.EndCondition3D
import endconditions.EndConditions3DChess
import gameTypes.AbstractChess
import gameTypes.GameType3D
import moveGenerators.MoveGenerator3D
import pieces.Piece3D
import rules.SpecialRules3D

/**
 * Represents a standard 2-player 3D chess game,
 * which contains no special rules and standard end conditions.
 *
 * @property rules the list of special rules (e.g. Castling, EnPassant)
 * @property endConditions the list of conditions that will end the game
 * @property startPlayer the player who goes first
 */
abstract class AbstractChess3D(
    rules: List<SpecialRules3D<AbstractChess3D>> = listOf(),
    endConditions: List<EndCondition3D<AbstractChess3D>> = listOf(EndConditions3DChess()),
    startPlayer: Int = 0
) :
    GameType3D,
    AbstractChess<Board3D, MoveGenerator3D, Piece3D, Coordinate3D>(rules, endConditions, startPlayer)
