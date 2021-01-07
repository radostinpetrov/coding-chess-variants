package moveGenerators

import boards.Board
import coordinates.Coordinate
import moves.Move
import pieces.Piece
import players.Player

interface MoveGenerator<B : Board<B, MG, M, P, C>, MG : MoveGenerator<B, MG, M, P, C>, M : Move<B, MG, M, P, C>, P : Piece<B, MG, M, P, C>, C : Coordinate> {
    fun generate(board: B, coordinate: C, piece: P, player: Player): List<M>
}
