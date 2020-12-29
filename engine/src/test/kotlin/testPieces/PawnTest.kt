package testPieces

import gameMoves.GameMove2D
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

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun checkPawnMoves() {
        mockStandardChess.initGame()
        val moves = mockStandardChess.getValidMoves()
        val firstPawn = (moves.first { it is GameMove2D.BasicGameMove && it.pieceMoved is StandardWhitePawn } as GameMove2D.BasicGameMove).pieceMoved
        val firstPawnMoves = moves.filter { it is GameMove2D.BasicGameMove && it.pieceMoved === firstPawn }
        Assertions.assertTrue(firstPawnMoves.size == 2)
        mockStandardChess.makeMove(firstPawnMoves[0])
    }
}
