package testPieces.hex

import coordinates.Coordinate2D
import gameTypes.hex.HexagonalChess
import io.mockk.MockKAnnotations
import io.mockk.spyk
import moves.BasicMoveHex
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HexKnightTest {
    val mockHexagonalChess = spyk<HexagonalChess>()
    val player = mockHexagonalChess.players[0]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun checkKnightMoves() {
        mockHexagonalChess.initGame()

        val knight = mockHexagonalChess.board.getPiece(Coordinate2D(3, 2))
        Assertions.assertNotNull(knight)

        val firstKnightMoves = mockHexagonalChess.getValidMoves().filter { it is BasicMoveHex && it.pieceMoved === knight }

        val expectedMoves = listOf(
            BasicMoveHex(Coordinate2D(3, 2), Coordinate2D(2, 7), knight!!, player, pieceCapturedCoordinate = Coordinate2D(2,7)),
            BasicMoveHex(Coordinate2D(3, 2), Coordinate2D(5, 6), knight, player, pieceCapturedCoordinate = Coordinate2D(5,6)),
            BasicMoveHex(Coordinate2D(3, 2), Coordinate2D(1, 6), knight, player, pieceCapturedCoordinate = Coordinate2D(1,6)),
            BasicMoveHex(Coordinate2D(3, 2), Coordinate2D(6, 3), knight, player, pieceCapturedCoordinate = Coordinate2D(6,3))
        )

        Assertions.assertEquals(expectedMoves, firstKnightMoves)
    }
}