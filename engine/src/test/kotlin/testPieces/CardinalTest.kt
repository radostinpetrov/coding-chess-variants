package testPieces

import moves.*
import gameTypes.chess.CapablancaChess
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.Cardinal

class CardinalTest {
    val mockCapablancaChess = spyk<CapablancaChess>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)
    @Test
    fun checkCardinalMoves() {
        mockCapablancaChess.initGame()
        val moves = mockCapablancaChess.getValidMoves(mockCapablancaChess.players[0])
        val firstCardinal = (moves.first { it is BasicMove2D && it.pieceMoved is Cardinal } as BasicMove2D).pieceMoved
        val firstCardinalMoves = moves.filter { it is BasicMove2D && it.pieceMoved === firstCardinal }
        Assertions.assertTrue(firstCardinalMoves.size == 2)
    }
}