package tutorial

import gameTypes.chess.AbstractChess

import boards.Board2D
import coordinates.Coordinate2D
import rules.ForcedCaptureRule
import winconditions.AntiChessWinConditions
import pieces.chess.*
import pieces.janggi.Elephant
import regions.CoordinateRegion

open class AntiChess : AbstractChess(listOf(ForcedCaptureRule()), listOf(AntiChessWinConditions())) {
    private val OutOfBoundsRegion = CoordinateRegion(3, 3)
    override val board: Board2D = Board2D(7, 7, OutOfBoundsRegion)
    override val name = "Tutorial Chess"

    override fun initBoard() {
        val player1 = players[0]
        val player2 = players[1]
        for (i in 0..7) {
            board.addPiece(Coordinate2D(i, 1), BerlinWhitePawn(player1))
            board.addPiece(Coordinate2D(i, 5), BerlinBlackPawn(player2))
        }
        board.addPiece(Coordinate2D(0, 0), Rook(player1))
        board.addPiece(Coordinate2D(6, 0), Rook(player1))
        board.addPiece(Coordinate2D(0, 6), Rook(player2))
        board.addPiece(Coordinate2D(6, 6), Rook(player2))
        board.addPiece(Coordinate2D(1, 0), Bishop(player1))
        board.addPiece(Coordinate2D(5, 0), Bishop(player1))
        board.addPiece(Coordinate2D(1, 6), Bishop(player2))
        board.addPiece(Coordinate2D(5, 6), Bishop(player2))
        board.addPiece(Coordinate2D(2, 0), Elephant(player1))
        board.addPiece(Coordinate2D(4, 0), Elephant(player1))
        board.addPiece(Coordinate2D(2, 6), Elephant(player2))
        board.addPiece(Coordinate2D(4, 6), Elephant(player2))
        board.addPiece(Coordinate2D(3, 0), King(player1))
        board.addPiece(Coordinate2D(3, 6), King(player2))
    }
}
