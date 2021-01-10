package rules

import moves.Move
import boards.Board
import coordinates.Coordinate
import moves.Move2D
import gameTypes.GameType
import moveGenerators.MoveGenerator
import pieces.Piece
import players.Player

/**
 * Represents special rules that can occur under certain conditions
 *
 * @param B the type of a board.
 * @param MG the type of a move generator.
 * @param P the type of a piece.
 * @param C the type of a coordinate.
 */
interface SpecialRules<out G: GameType<B, MG, P, C>,
        B : Board<B, MG, P, C>,
        MG : MoveGenerator<B, MG, P, C>,
        P: Piece<B, MG, P, C>,
        C: Coordinate> {
    /**
     * Modifies the given moves by either adding moves which satisfy a special condition,
     * or modifying the moves to make sure they all satisfy the condition.
     *
     * @return the possible moves generated as a result of the special rule
     */
    fun getPossibleMoves(game: @UnsafeVariance G, player: Player, moves: MutableList<Move<B, MG, P, C>>)
}