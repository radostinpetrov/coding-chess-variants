package gameTypes.chess

import Coordinate
import GameMove
import boards.Board2D
import gameTypes.chess.AbstractChess
import moves.visitors.Board2DMoveVisitor
import pieces.Bishop
import pieces.BlackPawn
import pieces.King
import pieces.Knight
import pieces.Piece
import pieces.Queen
import pieces.Rook
import pieces.WhitePawn
import players.Player

open class StandardChess : AbstractChess() {

    override val board = Board2D(8, 8)
    override val moveVisitor by lazy { Board2DMoveVisitor(board) }

    override fun initGame() {
        val player1 = players[0]
        val player2 = players[1]
        for (i in 0..7) {
            board.addPiece(Coordinate(i, 1), WhitePawn(player1))
            board.addPiece(Coordinate(i, 6), BlackPawn(player2))
        }
        board.addPiece(Coordinate(0, 0), Rook(player1))
        board.addPiece(Coordinate(7, 0), Rook(player1))
        board.addPiece(Coordinate(0, 7), Rook(player2))
        board.addPiece(Coordinate(7, 7), Rook(player2))
        board.addPiece(Coordinate(1, 0), Knight(player1))
        board.addPiece(Coordinate(6, 0), Knight(player1))
        board.addPiece(Coordinate(1, 7), Knight(player2))
        board.addPiece(Coordinate(6, 7), Knight(player2))
        board.addPiece(Coordinate(2, 0), Bishop(player1))
        board.addPiece(Coordinate(5, 0), Bishop(player1))
        board.addPiece(Coordinate(2, 7), Bishop(player2))
        board.addPiece(Coordinate(5, 7), Bishop(player2))
        board.addPiece(Coordinate(4, 0), King(player1))
        board.addPiece(Coordinate(4, 7), King(player2))
        board.addPiece(Coordinate(3, 0), Queen(player1))
        board.addPiece(Coordinate(3, 7), Queen(player2))
    }
}
