package endconditions

import boards.Board
import coordinates.Coordinate
import gameTypes.GameType
import moveGenerators.MoveGenerator
import moves.Move
import pieces.Piece
import players.Player

/**
 * Represents end conditions of the game.
 * Evaluated every turn to see if the game should end.
 *
 * @param B the type of a board.
 * @param MG the type of a move generator.
 * @param M the type of a move.
 * @param P the type of a piece.
 * @param C the type of a coordinate.
 */
interface EndCondition<out G : GameType<B, MG, M, P, C>,
    B : Board<B, MG, M, P, C>,
    MG : MoveGenerator<B, MG, M, P, C>,
    M : Move<B, MG, M, P, C>,
    P : Piece<B, MG, M, P, C>,
    C : Coordinate> {

    /**
     * Evaluate the condition and return an outcome if the game should end.
     *
     * @return an outcome if the game should end (i.e. it satisfies the condition),
     * otherwise returns null.
     */
    fun evaluate(game: @UnsafeVariance G, player: Player, moves: List<M>): Outcome?
}
