package moves

import boards.Board
import coordinates.Coordinate
import gameMoves.GameMove
import pieces.Piece
import players.Player

interface Move<B : Board<B, M, GM, P, C>, M : Move<B, M, GM, P, C>, GM: GameMove<B, M, GM, P, C>, P: Piece<B, M, GM, P, C>, C: Coordinate> {
    fun generate(board: B, coordinate: C, piece: P, player: Player): List<GM>
}