package testPieces

import gameMoves.GameMove2D
import gameTypes.chess.StandardChess
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.Queen
import players.Player

class QueenTest {
    val mockStandardChess = spyk<StandardChess>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun checkQueenMoves() {
        mockStandardChess.initGame()
        val moves = mockStandardChess.getValidMoves()
        val queenMoves = moves.filter { it is GameMove2D.BasicGameMove && it.pieceMoved is Queen }
        Assertions.assertTrue(queenMoves.isEmpty())
    }
}
