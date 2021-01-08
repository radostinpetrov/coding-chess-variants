package gameTypes.chess

import boards.Board2D
import coordinates.Coordinate2D
import rules.CapablancaCastling
import rules.Enpassant
import winconditions.StandardWinConditions
import pieces.chess.*

open class CapablancaChess : AbstractChess(listOf(CapablancaCastling(), Enpassant()), listOf(StandardWinConditions())) {
    override val board = Board2D(8, 10)
    override val name = "Capablanca Chess"

    override fun initBoard() {
        val player1 = players[0]
        val player2 = players[1]

        for (i in 0..9) {
            board.addPiece(Coordinate2D(i, 1), CapablancaWhitePawn(player1))
            board.addPiece(Coordinate2D(i, 6), CapablancaBlackPawn(player2))
        }

        board.addPiece(Coordinate2D(0, 0), Rook(player1))
        board.addPiece(Coordinate2D(9, 0), Rook(player1))
        board.addPiece(Coordinate2D(0, 7), Rook(player2))
        board.addPiece(Coordinate2D(9, 7), Rook(player2))
        board.addPiece(Coordinate2D(1, 0), Knight(player1))
        board.addPiece(Coordinate2D(8, 0), Knight(player1))
        board.addPiece(Coordinate2D(1, 7), Knight(player2))
        board.addPiece(Coordinate2D(8, 7), Knight(player2))
        board.addPiece(Coordinate2D(3, 0), Bishop(player1))
        board.addPiece(Coordinate2D(6, 0), Bishop(player1))
        board.addPiece(Coordinate2D(3, 7), Bishop(player2))
        board.addPiece(Coordinate2D(6, 7), Bishop(player2))
        board.addPiece(Coordinate2D(5, 0), King(player1))
        board.addPiece(Coordinate2D(5, 7), King(player2))
        board.addPiece(Coordinate2D(4, 0), Queen(player1))
        board.addPiece(Coordinate2D(4, 7), Queen(player2))
        board.addPiece(Coordinate2D(2, 0), Cardinal(player1))
        board.addPiece(Coordinate2D(2, 7), Cardinal(player2))
        board.addPiece(Coordinate2D(7, 0), Marshal(player1))
        board.addPiece(Coordinate2D(7, 7), Marshal(player2))
    }
}
