package testMoves

import coordinates.Coordinate2D
import gameTypes.chess.StandardChess
import io.mockk.spyk
import moves.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import pieces.chess.Bishop
import pieces.chess.StandardBlackPawn
import pieces.chess.StandardWhitePawn

class TestRemovePieceMove {
    val mockStandardChess = spyk<StandardChess>()

    val board = mockStandardChess.board
    val player1 = mockStandardChess.players[0]
    val player2 = mockStandardChess.players[1]

    @Test
    fun removePieceMoveLeavesSquareEmpty() {
        mockStandardChess.initGame()

        BasicMove2D(Coordinate2D(4, 1), Coordinate2D(4, 3), StandardWhitePawn(player1), player1)
        BasicMove2D(Coordinate2D(3, 6), Coordinate2D(3, 4), StandardBlackPawn(player2), player2)
        RemovePieceMove2D(player1, StandardWhitePawn(player2), Coordinate2D(3, 4))
        assertTrue(board.getPiece(Coordinate2D(3, 4)) == null)
    }

    @Test
    fun removePieceMoveFieldsTest() {
        val bishopPiece = Bishop(player1)
        board.addPiece(Coordinate2D(4, 4), bishopPiece)
        val removePieceMove = RemovePieceMove2D(player2, bishopPiece, Coordinate2D(34, 4))

        assertTrue(removePieceMove.displayFrom == Coordinate2D(-1, -1))
        assertTrue(removePieceMove.displayTo == Coordinate2D(-1, -1))
        assertTrue(removePieceMove.displayPiecePromotedTo == null)
        assertTrue(removePieceMove.displayPieceCaptured == null)
        assertTrue(removePieceMove.displayPieceMoved == bishopPiece)
    }
}
