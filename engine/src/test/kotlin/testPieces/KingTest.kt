package testPieces

import gameMoves.GameMove2D
import gameTypes.chess.StandardChess
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.King
import players.Player

class KingTest {
    val mockStandardChess = spyk<StandardChess>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun checkKingMoves() {
        mockStandardChess.initGame()
        val moves = mockStandardChess.getValidMoves()
        val kingMoves = moves.filter { it is GameMove2D.BasicGameMove && it.pieceMoved is King }
        Assertions.assertTrue(kingMoves.isEmpty())
    }
}
