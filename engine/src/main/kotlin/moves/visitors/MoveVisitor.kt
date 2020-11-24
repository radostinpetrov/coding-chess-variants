package moves.visitors

import Coordinate
import GameMove
import boards.Board
import moves.Move
import pieces.Piece
import players.Player

interface MoveVisitor<B> where B : Board<Piece> {
    val board: B
    fun visit(coordinate: Coordinate, piece: Piece, move: Move, player: Player): List<GameMove>
}
