package testPieces

import gameMoves.GameMove2D
import gameMoves.GameMove2D.SimpleGameMove
import gameMoves.GameMove2D.SimpleGameMove.BasicGameMove
import gameTypes.chess.StandardChess
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.Rook
import players.Player

class RookTest {
    val mockStandardChess = spyk<StandardChess>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun checkRookMoves() {
        mockStandardChess.initGame()
        val moves = mockStandardChess.getValidMoves(mockStandardChess.players[0])
        val rookMoves = moves.filter { it is BasicGameMove && it.pieceMoved is Rook }
        Assertions.assertTrue(rookMoves.isEmpty())
    }
}
