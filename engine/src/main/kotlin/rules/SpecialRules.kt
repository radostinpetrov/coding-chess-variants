package rules

import boards.Board
import coordinates.Coordinate
import moves.Move
import moves.Move2D
import gameTypes.GameType
import moveGenerators.MoveGenerator
import pieces.Piece
import players.Player

interface SpecialRules<out G: GameType<B, MG, M, P, C>,
        B : Board<B, MG, M, P, C>,
        MG : MoveGenerator<B, MG, M, P, C>,
        M: Move<B, MG, M, P, C>,
        P: Piece<B, MG, M, P, C>,
        C: Coordinate> {
    fun getPossibleMoves(game: @UnsafeVariance G, player: Player, moves: MutableList<Move2D>)
}