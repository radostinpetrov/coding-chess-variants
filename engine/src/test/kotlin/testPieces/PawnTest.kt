package testPieces

import moves.*
import gameTypes.chess.StandardChess
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.WhitePawn

class PawnTest {
    val mockStandardChess = spyk<StandardChess>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun checkPawnMoves() {
        mockStandardChess.initGame()
        val moves = mockStandardChess.getValidMoves(mockStandardChess.players[0])
        val firstPawn = (moves.first { it is BasicMove2D && it.pieceMoved is WhitePawn } as BasicMove2D).pieceMoved
        val firstPawnMoves = moves.filter { it is BasicMove2D && it.pieceMoved === firstPawn }
        Assertions.assertTrue(firstPawnMoves.size == 2)
    }
}
