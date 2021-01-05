package gameTypes

import boards.Board
import coordinates.Coordinate
import gameMoves.GameMove
import gameMoves.GameMove2D
import moves.Move
import pieces.Piece
import players.Player

interface SpecialRules<out G: GameType<B, M, GM, P, C>,
        B : Board<B, M, GM, P, C>,
        M : Move<B, M, GM, P, C>,
        GM: GameMove<B, M, GM, P, C>,
        P: Piece<B, M, GM, P, C>,
        C: Coordinate> {
    fun getPossibleMoves(game: @UnsafeVariance G, player: Player, moves: MutableList<GameMove2D>)
}