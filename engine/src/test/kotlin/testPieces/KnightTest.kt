package testPieces

import gameMoves.GameMove2D
import gameTypes.chess.StandardChess
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.Knight
import players.Player

class KnightTest {
    val mockStandardChess = spyk<StandardChess>()
    val mockHumanPlayer1 = mockk<Player>()
    val mockHumanPlayer2 = mockk<Player>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun checkKnightMoves() {
        mockStandardChess.addPlayer(mockHumanPlayer1)
        mockStandardChess.addPlayer(mockHumanPlayer2)
        mockStandardChess.initGame()
        val moves = mockStandardChess.getValidMoves(mockHumanPlayer1)
        val firstKnight = (moves.first { it is GameMove2D.BasicGameMove && it.pieceMoved is Knight } as GameMove2D.BasicGameMove).pieceMoved
        val firstKnightMoves = moves.filter { it is GameMove2D.BasicGameMove && it.pieceMoved === firstKnight }
        Assertions.assertTrue(firstKnightMoves.size == 2)
    }
}
