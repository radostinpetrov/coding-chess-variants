//package moves.visitors
//
//import Coordinate
//import gameMoves.GameMove
//import boards.Board
//import moves.Move2D
//import pieces.Piece
//import players.Player
//
//interface MoveVisitor<B> where B : Board<Piece> {
//    val board: B
//    fun visit(coordinate: Coordinate, piece: Piece, move: Move2D, player: Player): List<gameMoves.GameMove>
//}
