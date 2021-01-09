package rules

import boards.Board
import coordinates.Coordinate
import moves.Move
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
 * @param M the type of a move.
 * @param P the type of a piece.
 * @param C the type of a coordinate.
 */
interface SpecialRules<out G: GameType<B, MG, M, P, C>,
        B : Board<B, MG, M, P, C>,
        MG : MoveGenerator<B, MG, M, P, C>,
        M: Move<B, MG, M, P, C>,
        P: Piece<B, MG, M, P, C>,
        C: Coordinate> {
    /**
     * Modifies the given moves by either adding moves which satisfy a special condition,
     * or modifying the moves to make sure they all satisfy the condition.
     *
     * @return the possible moves generated as a result of the special rule
     */
    fun getPossibleMoves(game: @UnsafeVariance G, player: Player, moves: MutableList<Move2D>)
}