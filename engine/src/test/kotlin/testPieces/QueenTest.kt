package testPieces

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
    val mockHumanPlayer1 = mockk<Player>()
    val mockHumanPlayer2 = mockk<Player>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun checkQueenMoves() {
        // mockStandardChess.addPlayer(mockHumanPlayer1)
        // mockStandardChess.addPlayer(mockHumanPlayer2)
        mockStandardChess.initGame()
        val moves = mockStandardChess.getValidMoves(mockHumanPlayer1)
        val queenMoves = moves.filter { it is GameMove.BasicGameMove && it.pieceMoved is Queen }
        Assertions.assertTrue(queenMoves.size == 0)
    }
}
