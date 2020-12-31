package testPieces

import gameMoves.GameMove2D
import gameMoves.GameMove2D.SimpleGameMove.BasicGameMove
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
        val moves = mockStandardChess.getValidMoves(mockStandardChess.players[0])
        val firstPawn = (moves.first { it is BasicGameMove && it.pieceMoved is StandardWhitePawn } as BasicGameMove).pieceMoved
        val firstPawnMoves = moves.filter { it is BasicGameMove && it.pieceMoved === firstPawn }
        Assertions.assertTrue(firstPawnMoves.size == 2)
        mockStandardChess.makeMove(firstPawnMoves[0])
    }
}
