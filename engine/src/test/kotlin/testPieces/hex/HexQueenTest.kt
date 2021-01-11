package testPieces.hex

import coordinates.Coordinate2D
import gameTypes.hex.HexagonalChess
import io.mockk.MockKAnnotations
import io.mockk.spyk
import moves.BasicMoveHex
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HexQueenTest {
    val mockHexagonalChess = spyk<HexagonalChess>()
    val player = mockHexagonalChess.players[0]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun checkQueenMoves() {
        mockHexagonalChess.initGame()

        val queen = mockHexagonalChess.board.getPiece(Coordinate2D(2,3))
        Assertions.assertNotNull(queen)

        val firstRookMoves = mockHexagonalChess.getValidMoves().filter { it is BasicMoveHex && it.pieceMoved === queen }

        val expectedMoves = listOf(
            BasicMoveHex(Coordinate2D(2,3), Coordinate2D(3,4), queen!!, player, pieceCapturedCoordinate = Coordinate2D(3,4)),
            BasicMoveHex(Coordinate2D(2,3), Coordinate2D(4, 5), queen, player, pieceCapturedCoordinate = Coordinate2D(4,5)),
            BasicMoveHex(Coordinate2D(2,3), Coordinate2D(5, 6), queen, player, pieceCapturedCoordinate = Coordinate2D(5,6))
        )

        Assertions.assertEquals(expectedMoves, firstRookMoves)
    }
}