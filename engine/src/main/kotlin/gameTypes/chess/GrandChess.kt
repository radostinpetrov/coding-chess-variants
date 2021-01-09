package gameTypes.chess

import coordinates.Coordinate2D
import boards.Board2D
import rules.Enpassant
import endconditions.StandardEndConditions
import pieces.chess.*

class GrandChess : AbstractChess(listOf(Enpassant()), listOf(StandardEndConditions())) {
    override val board = Board2D(10, 10)
    override val name = "Grand Chess"

    override fun initBoard() {
        val player1 = players[0]
        val player2 = players[1]
        for (i in 0..9) {
            board.addPiece(Coordinate2D(i, 2), GrandWhitePawn(player1))
            board.addPiece(Coordinate2D(i, 7), GrandBlackPawn(player2))
        }
        board.addPiece(Coordinate2D(0, 0), Rook(player1))
        board.addPiece(Coordinate2D(9, 0), Rook(player1))
        board.addPiece(Coordinate2D(9, 9), Rook(player2))
        board.addPiece(Coordinate2D(0, 9), Rook(player2))
        board.addPiece(Coordinate2D(2, 1), Bishop(player1))
        board.addPiece(Coordinate2D(7, 1), Bishop(player1))
        board.addPiece(Coordinate2D(2, 8), Bishop(player2))
        board.addPiece(Coordinate2D(7, 8), Bishop(player2))
        board.addPiece(Coordinate2D(1, 1), Knight(player1))
        board.addPiece(Coordinate2D(8, 1), Knight(player1))
        board.addPiece(Coordinate2D(1, 8), Knight(player2))
        board.addPiece(Coordinate2D(5, 1), Marshal(player1))
        board.addPiece(Coordinate2D(5, 8), Marshal(player2))
        board.addPiece(Coordinate2D(6, 1), Cardinal(player1))
        board.addPiece(Coordinate2D(6, 8), Cardinal(player2))
        board.addPiece(Coordinate2D(8, 8), Knight(player2))
        board.addPiece(Coordinate2D(3, 1), Queen(player1))
        board.addPiece(Coordinate2D(3, 8), Queen(player2))
        board.addPiece(Coordinate2D(4, 1), King(player1))
        board.addPiece(Coordinate2D(4, 8), King(player2))
    }
}
