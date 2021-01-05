package testRules

import coordinates.Coordinate2D
import gameTypes.chess.StandardChess
import gameTypes.chess.winconditions.InsufficientMaterialStalemate
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.Bishop
import pieces.chess.King
import pieces.chess.Knight

class InsufficientMaterialStalemateTest {
    val mockStandardChess = spyk<StandardChess>()
    val board = mockStandardChess.board

    val player1 = mockStandardChess.players[0]
    val player2 = mockStandardChess.players[1]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun twoSoleKings() {
        board.addPiece(Coordinate2D(3, 3), King(player1))
        board.addPiece(Coordinate2D(5, 5), King(player2))
        val outcome = InsufficientMaterialStalemate().evaluate(mockStandardChess, player1, listOf())

        assertEquals(Outcome.Draw("Stalemate by Insufficient Material"), outcome)
    }

    @Test
    fun kingBishopVsSoleKing() {
        board.addPiece(Coordinate2D(3, 3), King(player1))
        board.addPiece(Coordinate2D(5, 5), King(player2))
        board.addPiece(Coordinate2D(2, 5), Bishop(player1))
        val outcome = InsufficientMaterialStalemate().evaluate(mockStandardChess, player1, listOf())

        assertEquals(Outcome.Draw("Stalemate by Insufficient Material"), outcome)
    }

    @Test
    fun kingTwoSameBishopsVsSoleKing() {
        board.addPiece(Coordinate2D(3, 3), King(player1))
        board.addPiece(Coordinate2D(5, 5), King(player2))
        board.addPiece(Coordinate2D(3, 4), Bishop(player1))
        board.addPiece(Coordinate2D(2, 5), Bishop(player1))
        val outcome = InsufficientMaterialStalemate().evaluate(mockStandardChess, player1, listOf())

        assertEquals(Outcome.Draw("Stalemate by Insufficient Material"), outcome)
    }

    @Test
    fun kingKnightVsSoleKing() {
        board.addPiece(Coordinate2D(3, 3), King(player1))
        board.addPiece(Coordinate2D(5, 5), King(player2))
        board.addPiece(Coordinate2D(0, 0), Knight(player1))
        val outcome = InsufficientMaterialStalemate().evaluate(mockStandardChess, player1, listOf())

        assertEquals(Outcome.Draw("Stalemate by Insufficient Material"), outcome)
    }
}
