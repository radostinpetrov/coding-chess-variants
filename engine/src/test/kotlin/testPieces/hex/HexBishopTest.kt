package testPieces.hex

import coordinates.Coordinate2D
import gameTypes.hex.HexagonalChess
import io.mockk.MockKAnnotations
import io.mockk.spyk
import moves.BasicMoveHex
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HexBishopTest {
    val mockHexagonalChess = spyk<HexagonalChess>()
    val player = mockHexagonalChess.players[0]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun checkBishopMoves() {
        mockHexagonalChess.initGame()

        val bishop = mockHexagonalChess.board.getPiece(Coordinate2D(5, 4))
        Assertions.assertNotNull(bishop)

        val firstBishopMoves = mockHexagonalChess.getValidMoves().filter { it is BasicMoveHex && it.pieceMoved === bishop }

        val expectedMoves = listOf(
            BasicMoveHex(Coordinate2D(5, 4), Coordinate2D(7, 4), bishop!!, player, pieceCapturedCoordinate = Coordinate2D(7,4)),
            BasicMoveHex(Coordinate2D(5, 4), Coordinate2D(3,4), bishop, player, pieceCapturedCoordinate = Coordinate2D(3,4))
        )

        Assertions.assertEquals(expectedMoves, firstBishopMoves)
    }
}