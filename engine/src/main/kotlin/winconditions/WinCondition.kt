package winconditions

import Outcome
import boards.Board
import coordinates.Coordinate
import moves.Move
import moves.Move2D
import gameTypes.GameType
import moveGenerators.MoveGenerator
import pieces.Piece
import players.Player

interface WinCondition<G: GameType<B, MG, M, P, C>,
        B : Board<B, MG, M, P, C>,
        MG : MoveGenerator<B, MG, M, P, C>,
        M: Move<B, MG, M, P, C>,
        P: Piece<B, MG, M, P, C>,
        C: Coordinate> {
    fun evaluate(game: @UnsafeVariance G, player: Player, moves: List<Move2D>): Outcome?
}