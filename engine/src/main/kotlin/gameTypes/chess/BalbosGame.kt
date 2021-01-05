package gameTypes.chess

import boards.Board2D
import coordinates.Coordinate2D
import gameTypes.chess.rules.ForcedCaptureRule
import gameTypes.chess.winconditions.AntiChessWinConditions
import moves.region.BoxRegion
import moves.region.CompositeRegion
import pieces.antichess.AntiChessKing
import pieces.chess.*

class BalbosGame() : AbstractChess(listOf(ForcedCaptureRule()), listOf(AntiChessWinConditions())) {
    private val OutOfBoundsRegion = CompositeRegion(
        listOf(
            BoxRegion(Coordinate2D(0, 0), Coordinate2D(3, 0)),
            BoxRegion(Coordinate2D(0, 0), Coordinate2D(1, 2)),
            BoxRegion(Coordinate2D(0, 0), Coordinate2D(2, 1)),
            BoxRegion(Coordinate2D(0, 0), Coordinate2D(0, 3)),
            BoxRegion(Coordinate2D(0, 9), Coordinate2D(3, 9)),
            BoxRegion(Coordinate2D(0, 9), Coordinate2D(2, 8)),
            BoxRegion(Coordinate2D(0, 9), Coordinate2D(1, 7)),
            BoxRegion(Coordinate2D(0, 9), Coordinate2D(0, 6)),
            BoxRegion(Coordinate2D(10, 9), Coordinate2D(7, 9)),
            BoxRegion(Coordinate2D(10, 9), Coordinate2D(8, 8)),
            BoxRegion(Coordinate2D(10, 9), Coordinate2D(9, 7)),
            BoxRegion(Coordinate2D(10, 9), Coordinate2D(10, 6)),
            BoxRegion(Coordinate2D(10, 0), Coordinate2D(7, 0)),
            BoxRegion(Coordinate2D(10, 0), Coordinate2D(8, 1)),
            BoxRegion(Coordinate2D(10, 0), Coordinate2D(9, 2)),
            BoxRegion(Coordinate2D(10, 0), Coordinate2D(10, 3)),
        )
    )
    override val board: Board2D = Board2D(10, 10, OutOfBoundsRegion)

    override fun initGame() {
        val player1 = players[0]
        val player2 = players[1]
        board.addPiece(Coordinate2D(4, 0), King(player1))
        board.addPiece(Coordinate2D(5, 0), Bishop(player1))
        board.addPiece(Coordinate2D(6, 0), Queen(player1))
        board.addPiece(Coordinate2D(3, 1), Rook(player1))
        board.addPiece(Coordinate2D(4, 1), Knight(player1))
        board.addPiece(Coordinate2D(5, 1), Bishop(player1))
        board.addPiece(Coordinate2D(6, 1), Knight(player1))
        board.addPiece(Coordinate2D(7, 1), Rook(player1))
        board.addPiece(Coordinate2D(2, 2), StandardWhitePawn(player1))
        board.addPiece(Coordinate2D(3, 2), StandardWhitePawn(player1))
        board.addPiece(Coordinate2D(4, 2), StandardWhitePawn(player1))
        board.addPiece(Coordinate2D(5, 2), StandardWhitePawn(player1))
        board.addPiece(Coordinate2D(6, 2), StandardWhitePawn(player1))
        board.addPiece(Coordinate2D(7, 2), StandardWhitePawn(player1))
        board.addPiece(Coordinate2D(8, 2), StandardWhitePawn(player1))

        board.addPiece(Coordinate2D(4, 9), King(player2))
        board.addPiece(Coordinate2D(5, 9), Bishop(player2))
        board.addPiece(Coordinate2D(6, 9), Queen(player2))
        board.addPiece(Coordinate2D(3, 8), Rook(player2))
        board.addPiece(Coordinate2D(4, 8), Knight(player2))
        board.addPiece(Coordinate2D(5, 8), Bishop(player2))
        board.addPiece(Coordinate2D(6, 8), Knight(player2))
        board.addPiece(Coordinate2D(7, 8), Rook(player2))
        board.addPiece(Coordinate2D(2, 7), StandardBlackPawn(player2))
        board.addPiece(Coordinate2D(3, 7), StandardBlackPawn(player2))
        board.addPiece(Coordinate2D(4, 7), StandardBlackPawn(player2))
        board.addPiece(Coordinate2D(5, 7), StandardBlackPawn(player2))
        board.addPiece(Coordinate2D(6, 7), StandardBlackPawn(player2))
        board.addPiece(Coordinate2D(7, 7), StandardBlackPawn(player2))
        board.addPiece(Coordinate2D(8, 7), StandardBlackPawn(player2))
    }
}
