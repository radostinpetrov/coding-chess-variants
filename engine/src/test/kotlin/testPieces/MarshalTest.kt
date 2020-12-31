package testPieces

import gameMoves.GameMove2D
import gameMoves.GameMove2D.SimpleGameMove.BasicGameMove
import gameTypes.chess.CapablancaChess
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.Marshal

class MarshalTest {
    val mockCapablancaChess = spyk<CapablancaChess>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)
    @Test
    fun checkMarshalMoves() {
        mockCapablancaChess.initGame()
        val moves = mockCapablancaChess.getValidMoves(mockCapablancaChess.players[0])
        val firstMarshal = (moves.first { it is BasicGameMove && it.pieceMoved is Marshal } as BasicGameMove).pieceMoved
        val firstMarshalMoves = moves.filter { it is BasicGameMove && it.pieceMoved === firstMarshal }
        Assertions.assertTrue(firstMarshalMoves.size == 2)
    }
}