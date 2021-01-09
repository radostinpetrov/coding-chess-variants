package endconditions

import boards.Board
import coordinates.Coordinate
import gameTypes.GameType
import moveGenerators.MoveGenerator
import moves.Move
import moves.Move2D
import pieces.Piece
import players.Player

/**
 * Represents win conditions of the game
 * Evaluated every turn to see if the game should end
 * e.g. Checkmate, Stalemate by no legal moves etc.
 *
 * @param B the type of a board.
 * @param MG the type of a move generator.
 * @param M the type of a move.
 * @param P the type of a piece.
 * @param C the type of a coordinate.
 */
interface EndCondition<G : GameType<B, MG, M, P, C>,
    B : Board<B, MG, M, P, C>,
    MG : MoveGenerator<B, MG, M, P, C>,
    M : Move<B, MG, M, P, C>,
    P : Piece<B, MG, M, P, C>,
    C : Coordinate> {
    /**
     * @return an outcome if the game should end if it satisfies the condition,
     * otherwise returns null.
     */
    fun evaluate(game: @UnsafeVariance G, player: Player, moves: List<Move2D>): Outcome?
}
