package tutorial

import boards.Board2D
import coordinates.Coordinate2D
import gameTypes.chess.AbstractChess
import pieces.chess.*
import regions.CoordinateRegion
import winconditions.Checkmate
import winconditions.NoLegalMovesStalemate
import winconditions.ThreeFoldRepetitionStalemate

open class TutorialChess : AbstractChess(listOf(NoRepeatedMoveFromSamePieceRule()), listOf(Checkmate(), TutorialWinCondition(), ThreeFoldRepetitionStalemate(), NoLegalMovesStalemate())) {
    private val outOfBoundsRegion = CoordinateRegion(3, 3)
    override val board: Board2D = Board2D(7, 7, outOfBoundsRegion)
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
        board.addPiece(Coordinate2D(2, 0), Alfil(player1))
        board.addPiece(Coordinate2D(4, 0), Alfil(player1))
        board.addPiece(Coordinate2D(2, 6), Alfil(player2))
        board.addPiece(Coordinate2D(4, 6), Alfil(player2))
        board.addPiece(Coordinate2D(3, 0), King(player1))
        board.addPiece(Coordinate2D(3, 6), King(player2))
    }
}
