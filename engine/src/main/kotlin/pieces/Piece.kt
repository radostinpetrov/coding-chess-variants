package pieces

import boards.Board
import coordinates.Coordinate
import gameMoves.GameMove
import moves.Move
import players.Player

interface Piece<B : Board<B, M, GM, P, C>, M : Move<B, M, GM, P, C>, GM: GameMove<B, M, GM, P, C>, P: Piece<B, M, GM, P, C>, C: Coordinate> {
    val moveTypes: List<M>
    val player: Player
    fun getSymbol(): String
}

//nterface Piece<T : Move> {
//    val moveTypes: List<Move2D>
//    val player: Player
//    fun getSymbol(): String
//}
