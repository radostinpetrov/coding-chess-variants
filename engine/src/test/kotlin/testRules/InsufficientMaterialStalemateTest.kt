package testRules

import coordinates.Coordinate2D
import gameTypes.chess.CapablancaChess
import gameTypes.chess.GrandChess
import gameTypes.chess.StandardChess
import endconditions.InsufficientMaterialStalemate
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.*
import endconditions.Outcome
import org.junit.jupiter.api.Assertions.assertFalse

class InsufficientMaterialStalemateTest {
    val mockStandardChess = spyk<StandardChess>()
    val mockCapablancaChess = spyk<CapablancaChess>()
    val mockGrandChess = spyk<GrandChess>()
    val standardChessBoard = mockStandardChess.board
    val capablancaChessBoard = mockCapablancaChess.board
    val grandChessBoard = mockGrandChess.board

    val standardPlayer1 = mockStandardChess.players[0]
    val standardPlayer2 = mockStandardChess.players[1]
    val capablancaPlayer1 = mockCapablancaChess.players[0]
    val capablancaPlayer2 = mockCapablancaChess.players[1]
    val grandChessPlayer1 = mockGrandChess.players[0]
    val grandChessPlayer2 = mockGrandChess.players[1]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun twoSoleKings() {
        standardChessBoard.addPiece(Coordinate2D(3, 3), King(standardPlayer1))
        standardChessBoard.addPiece(Coordinate2D(5, 5), King(standardPlayer2))
        val outcome = InsufficientMaterialStalemate().evaluate(mockStandardChess, standardPlayer1, listOf())

        assertEquals(Outcome.Draw("by Insufficient Material"), outcome)
    }

    @Test
    fun kingBishopVsSoleKing() {
        standardChessBoard.addPiece(Coordinate2D(3, 3), King(standardPlayer1))
        standardChessBoard.addPiece(Coordinate2D(5, 5), King(standardPlayer2))
        standardChessBoard.addPiece(Coordinate2D(2, 5), Bishop(standardPlayer1))
        val outcome = InsufficientMaterialStalemate().evaluate(mockStandardChess, standardPlayer1, listOf())

        assertEquals(Outcome.Draw("by Insufficient Material"), outcome)
    }

    @Test
    fun kingTwoSameBishopsVsSoleKing() {
        standardChessBoard.addPiece(Coordinate2D(3, 3), King(standardPlayer1))
        standardChessBoard.addPiece(Coordinate2D(5, 5), King(standardPlayer2))
        standardChessBoard.addPiece(Coordinate2D(3, 4), Bishop(standardPlayer1))
        standardChessBoard.addPiece(Coordinate2D(2, 5), Bishop(standardPlayer1))
        val outcome = InsufficientMaterialStalemate().evaluate(mockStandardChess, standardPlayer1, listOf())

        assertEquals(Outcome.Draw("by Insufficient Material"), outcome)
    }

    @Test
    fun kingKnightVsSoleKing() {
        standardChessBoard.addPiece(Coordinate2D(3, 3), King(standardPlayer1))
        standardChessBoard.addPiece(Coordinate2D(5, 5), King(standardPlayer2))
        standardChessBoard.addPiece(Coordinate2D(0, 0), Knight(standardPlayer1))
        val outcome = InsufficientMaterialStalemate().evaluate(mockStandardChess, standardPlayer1, listOf())

        assertEquals(Outcome.Draw("by Insufficient Material"), outcome)
    }

    @Test
    fun kingKnightVsSoleKingCapablanca() {
        capablancaChessBoard.addPiece(Coordinate2D(3, 3), King(capablancaPlayer1))
        capablancaChessBoard.addPiece(Coordinate2D(5, 5), King(capablancaPlayer2))
        capablancaChessBoard.addPiece(Coordinate2D(0, 0), Knight(capablancaPlayer1))
        val outcome = InsufficientMaterialStalemate().evaluate(mockCapablancaChess, capablancaPlayer1, listOf())

        assertEquals(Outcome.Draw("by Insufficient Material"), outcome)
    }
    @Test
    fun kingKnightVsSoleKingGrand() {
        grandChessBoard.addPiece(Coordinate2D(3, 3), King(grandChessPlayer1))
        grandChessBoard.addPiece(Coordinate2D(5, 5), King(grandChessPlayer2))
        grandChessBoard.addPiece(Coordinate2D(0, 0), Knight(grandChessPlayer1))
        val outcome = InsufficientMaterialStalemate().evaluate(mockGrandChess, grandChessPlayer1, listOf())

        assertEquals(Outcome.Draw("by Insufficient Material"), outcome)
    }

    @Test
    fun kingFourSameBishopsGrand() {
        grandChessBoard.addPiece(Coordinate2D(0, 0), Bishop(grandChessPlayer1))
        grandChessBoard.addPiece(Coordinate2D(1, 1), Bishop(grandChessPlayer1))
        grandChessBoard.addPiece(Coordinate2D(2, 2), Bishop(grandChessPlayer1))
        grandChessBoard.addPiece(Coordinate2D(3, 3), Bishop(grandChessPlayer1))
        grandChessBoard.addPiece(Coordinate2D(2, 5), King(grandChessPlayer1))
        grandChessBoard.addPiece(Coordinate2D(3, 9), King(grandChessPlayer2))
        val outcome = InsufficientMaterialStalemate().evaluate(mockGrandChess, grandChessPlayer1, listOf())

        assertEquals(Outcome.Draw("by Insufficient Material"), outcome)
    }

    @Test
    fun twoSoleKingsPawnIsNotStalemate() {
        capablancaChessBoard.addPiece(Coordinate2D(3, 3), King(capablancaPlayer1))
        capablancaChessBoard.addPiece(Coordinate2D(6, 6), King(capablancaPlayer2))
        capablancaChessBoard.addPiece(Coordinate2D(3, 5), StandardWhitePawn(capablancaPlayer1))

        val outcome = InsufficientMaterialStalemate().evaluate(mockGrandChess, grandChessPlayer1, listOf())

        assertFalse(outcome == Outcome.Draw("by Insufficient Material"))
    }
}
