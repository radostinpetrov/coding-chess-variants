package testPieces

import gameMoves.GameMove2D
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
        val moves = mockStandardChess.getValidMoves()
        val rookMoves = moves.filter { it is GameMove2D.BasicGameMove && it.pieceMoved is Rook }
        Assertions.assertTrue(rookMoves.isEmpty())
    }
}
