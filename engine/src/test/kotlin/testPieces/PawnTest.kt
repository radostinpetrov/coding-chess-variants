package testPieces

import gameTypes.chess.StandardChess
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.StandardWhitePawn
import players.Player

class PawnTest {
    val mockStandardChess = spyk<StandardChess>()
    val mockHumanPlayer1 = mockk<Player>()
    val mockHumanPlayer2 = mockk<Player>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun checkPawnMoves() {
        // mockStandardChess.addPlayer(mockHumanPlayer1)
        // mockStandardChess.addPlayer(mockHumanPlayer2)
        mockStandardChess.initGame()
        val moves = mockStandardChess.getValidMoves(mockHumanPlayer1)
        val firstPawn = (moves.first { it is GameMove.BasicGameMove && it.pieceMoved is StandardWhitePawn } as GameMove.BasicGameMove).pieceMoved
        val firstPawnMoves = moves.filter { it is GameMove.BasicGameMove && (it as GameMove.BasicGameMove).pieceMoved === firstPawn }
        Assertions.assertTrue(firstPawnMoves.size == 2)
        mockStandardChess.makeMove(firstPawnMoves[0])
    }
}
