package main.kotlin.gameTypes.chess

import main.kotlin.Coordinate
import main.kotlin.boards.Board2D
import main.kotlin.moves.visitors.Board2DMoveVisitor
import pieces.Bishop
import pieces.BlackPawn
import pieces.Cardinal
import pieces.King
import pieces.Knight
import pieces.Marshal
import pieces.Queen
import pieces.Rook
import pieces.WhitePawn

class
GrandChess : AbstractChess() {
    override val board = Board2D(10, 10)
    override val moveVisitor by lazy { Board2DMoveVisitor(board) }

    override fun initGame() {
        val player1 = players[0]
        val player2 = players[1]
        for (i in 0..9) {
            board.addPiece(Coordinate(i, 2), WhitePawn(player1))
            board.addPiece(Coordinate(i, 7), BlackPawn(player2))
        }
        board.addPiece(Coordinate(0, 0), Rook(player1))
        board.addPiece(Coordinate(9, 0), Rook(player1))
        board.addPiece(Coordinate(9, 9), Rook(player2))
        board.addPiece(Coordinate(0, 9), Rook(player2))
        board.addPiece(Coordinate(2, 1), Bishop(player1))
        board.addPiece(Coordinate(7, 1), Bishop(player1))
        board.addPiece(Coordinate(2, 8), Bishop(player2))
        board.addPiece(Coordinate(7, 8), Bishop(player2))
        board.addPiece(Coordinate(1, 1), Knight(player1))
        board.addPiece(Coordinate(8, 1), Knight(player1))
        board.addPiece(Coordinate(1, 8), Knight(player2))
        board.addPiece(Coordinate(5, 1), Marshal(player1))
        board.addPiece(Coordinate(5, 8), Marshal(player2))
        board.addPiece(Coordinate(6, 1), Cardinal(player1))
        board.addPiece(Coordinate(6, 8), Cardinal(player2))
        board.addPiece(Coordinate(8, 8), Knight(player2))
        board.addPiece(Coordinate(3, 1), Queen(player1))
        board.addPiece(Coordinate(3, 8), Queen(player2))
        board.addPiece(Coordinate(4, 1), King(player1))
        board.addPiece(Coordinate(4, 8), King(player2))
    }
}
