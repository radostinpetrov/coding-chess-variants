package testPieces.hex

import coordinates.Coordinate2D
import gameTypes.hex.HexagonalChess
import io.mockk.MockKAnnotations
import io.mockk.spyk
import moves.BasicMoveHex
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HexPawnTest {
    val mockHexagonalChess = spyk<HexagonalChess>()
    val player = mockHexagonalChess.players[0]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun checkPawnMoves() {
        mockHexagonalChess.initGame()
        val pawn = mockHexagonalChess.board.getPiece(Coordinate2D(1,4))
        Assertions.assertNotNull(pawn)

        val firstPawnMoves = mockHexagonalChess.getValidMoves().filter { it is BasicMoveHex && it.pieceMoved === pawn }

        val expectedMoves = listOf(
            BasicMoveHex(Coordinate2D(1,4), Coordinate2D(1,8), pawn!!, player, pieceCapturedCoordinate = Coordinate2D(1,8)),
            BasicMoveHex(Coordinate2D(1,4), Coordinate2D(1, 6), pawn, player, pieceCapturedCoordinate = Coordinate2D(1,6))
            )

        Assertions.assertEquals(expectedMoves, firstPawnMoves)
    }
}