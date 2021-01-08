package testMoves

import coordinates.Coordinate2D
import gameTypes.chess.StandardChess
import io.mockk.spyk
import moves.Move2D
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import pieces.chess.Knight
import pieces.chess.Queen
import pieces.chess.StandardWhitePawn

class AddPieceMove {
    val mockStandardChess = spyk<StandardChess>()

    val board = mockStandardChess.board
    val player1 = mockStandardChess.players[0]
    val player2 = mockStandardChess.players[1]

    @Test
    fun addPieceMoveFieldsTest() {
        val knightPiece = Knight(player1)
        val addPieceMove = Move2D.SimpleMove.AddPieceMove(player1, knightPiece, Coordinate2D(3,3))

        assertTrue(addPieceMove.displayFrom == Coordinate2D(3, 3))
        assertTrue(addPieceMove.displayTo == Coordinate2D(3, 3))
        assertTrue(addPieceMove.displayPiecePromotedTo == null)
        assertTrue(addPieceMove.displayPieceCaptured == null)
        assertTrue(addPieceMove.displayPieceMoved == knightPiece)
    }
}