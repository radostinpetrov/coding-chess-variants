package testPieces

import moves.Move.SimpleMove.BasicMove
import gameTypes.chess.StandardChess
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.Queen

class QueenTest {
    val mockStandardChess = spyk<StandardChess>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun checkQueenMoves() {
        mockStandardChess.initGame()
        val moves = mockStandardChess.getValidMoves(mockStandardChess.players[0])
        val queenMoves = moves.filter { it is BasicMove && it.pieceMoved is Queen }
        Assertions.assertTrue(queenMoves.isEmpty())
    }
}
