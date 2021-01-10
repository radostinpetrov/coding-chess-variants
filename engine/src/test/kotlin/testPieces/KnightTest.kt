package testPieces

import moves.*
import gameTypes.chess.StandardChess
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.Knight

class KnightTest {
    val mockStandardChess = spyk<StandardChess>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun checkKnightMoves() {
        mockStandardChess.initGame()
        val moves = mockStandardChess.getValidMoves(mockStandardChess.players[0])
        val firstKnight = (moves.first { it is BasicMove2D && it.pieceMoved is Knight } as BasicMove2D).pieceMoved
        val firstKnightMoves = moves.filter { it is BasicMove2D && it.pieceMoved === firstKnight }
        Assertions.assertTrue(firstKnightMoves.size == 2)
    }
}
