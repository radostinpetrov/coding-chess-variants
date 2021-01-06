package pieces

import boards.Board
import coordinates.Coordinate
import moves.Move
import moveGenerators.MoveGenerator
import players.Player

interface Piece<B : Board<B, MG, M, P, C>, MG : MoveGenerator<B, MG, M, P, C>, M: Move<B, MG, M, P, C>, P: Piece<B, MG, M, P, C>, C: Coordinate> {
    val moveGenerators: List<MG>
    val player: Player
    fun getSymbol(): String
}