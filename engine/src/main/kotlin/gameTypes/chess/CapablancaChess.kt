package main.kotlin.gameTypes.chess

import main.kotlin.Coordinate
import main.kotlin.boards.Board2D
import main.kotlin.gameTypes.chess.rules.CapablancaCastling
import main.kotlin.gameTypes.chess.rules.Enpassant
import main.kotlin.moves.visitors.Board2DMoveVisitor
import main.kotlin.pieces.chess.*

open class CapablancaChess : AbstractChess(listOf(CapablancaCastling(), Enpassant())) {

    override val board = Board2D(8, 10)
    override val moveVisitor by lazy { Board2DMoveVisitor(board) }

    override fun initGame() {
        val player1 = players[0]
        val player2 = players[1]
        for (i in 0..9) {
            board.addPiece(Coordinate(i, 1), WhitePawn(player1))
            board.addPiece(Coordinate(i, 6), BlackPawn(player2))
        }
        board.addPiece(Coordinate(0, 0), Rook(player1))
        board.addPiece(Coordinate(9, 0), Rook(player1))
        board.addPiece(Coordinate(0, 7), Rook(player2))
        board.addPiece(Coordinate(9, 7), Rook(player2))
        board.addPiece(Coordinate(1, 0), Knight(player1))
        board.addPiece(Coordinate(8, 0), Knight(player1))
        board.addPiece(Coordinate(1, 7), Knight(player2))
        board.addPiece(Coordinate(8, 7), Knight(player2))
        board.addPiece(Coordinate(3, 0), Bishop(player1))
        board.addPiece(Coordinate(6, 0), Bishop(player1))
        board.addPiece(Coordinate(3, 7), Bishop(player2))
        board.addPiece(Coordinate(6, 7), Bishop(player2))
        board.addPiece(Coordinate(5, 0), King(player1))
        board.addPiece(Coordinate(5, 7), King(player2))
        board.addPiece(Coordinate(4, 0), Queen(player1))
        board.addPiece(Coordinate(4, 7), Queen(player2))
        board.addPiece(Coordinate(2, 0), Cardinal(player1))
        board.addPiece(Coordinate(2, 7), Cardinal(player2))
        board.addPiece(Coordinate(7, 0), Marshal(player1))
        board.addPiece(Coordinate(7, 7), Marshal(player2))
    }
}
