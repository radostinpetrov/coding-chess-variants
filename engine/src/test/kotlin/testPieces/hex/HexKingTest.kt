package testPieces.hex

import coordinates.Coordinate2D
import gameTypes.hex.HexagonalChess
import io.mockk.MockKAnnotations
import io.mockk.spyk
import moves.BasicMoveHex
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HexKingTest {
    val mockHexagonalChess = spyk<HexagonalChess>()
    val player = mockHexagonalChess.players[0]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun checkKingMoves() {
        mockHexagonalChess.initGame()

        val king = mockHexagonalChess.board.getPiece(Coordinate2D(6, 1))
        Assertions.assertNotNull(king)

        val firstRookMoves = mockHexagonalChess.getValidMoves().filter { it is BasicMoveHex && it.pieceMoved === king }

        val expectedMoves = listOf(
            BasicMoveHex(Coordinate2D(6, 1), Coordinate2D(6, 3), king!!, player, pieceCapturedCoordinate = Coordinate2D(6,3)),
            BasicMoveHex(Coordinate2D(6, 1), Coordinate2D(7, 4), king, player, pieceCapturedCoordinate = Coordinate2D(7,4))
        )

        Assertions.assertEquals(expectedMoves, firstRookMoves)
    }
}