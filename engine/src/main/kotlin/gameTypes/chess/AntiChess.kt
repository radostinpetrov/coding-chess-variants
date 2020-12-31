package gameTypes.chess

import boards.Board2D
import coordinates.Coordinate2D
import gameTypes.chess.rules.ForcedCaptureRule
import gameTypes.chess.winconditions.AntiChessWinConditions
import pieces.antichess.AntiChessBlackPawn
import pieces.antichess.AntiChessKing
import pieces.antichess.AntiChessWhitePawn
import pieces.chess.*

open class AntiChess : AbstractChess(listOf(ForcedCaptureRule()), listOf(AntiChessWinConditions())) {
    override val board = Board2D(8, 8)

    override fun initGame() {
        val player1 = players[0]
        val player2 = players[1]
        for (i in 0..7) {
            board.addPiece(Coordinate2D(i, 1), AntiChessWhitePawn(player1))
            board.addPiece(Coordinate2D(i, 6), AntiChessBlackPawn(player2))
        }
        board.addPiece(Coordinate2D(0, 0), Rook(player1))
        board.addPiece(Coordinate2D(7, 0), Rook(player1))
        board.addPiece(Coordinate2D(0, 7), Rook(player2))
        board.addPiece(Coordinate2D(7, 7), Rook(player2))
        board.addPiece(Coordinate2D(1, 0), Knight(player1))
        board.addPiece(Coordinate2D(6, 0), Knight(player1))
        board.addPiece(Coordinate2D(1, 7), Knight(player2))
        board.addPiece(Coordinate2D(6, 7), Knight(player2))
        board.addPiece(Coordinate2D(2, 0), Bishop(player1))
        board.addPiece(Coordinate2D(5, 0), Bishop(player1))
        board.addPiece(Coordinate2D(2, 7), Bishop(player2))
        board.addPiece(Coordinate2D(5, 7), Bishop(player2))
        board.addPiece(Coordinate2D(4, 0), AntiChessKing(player1))
        board.addPiece(Coordinate2D(4, 7), AntiChessKing(player2))
        board.addPiece(Coordinate2D(3, 0), Queen(player1))
        board.addPiece(Coordinate2D(3, 7), Queen(player2))
    }
}
