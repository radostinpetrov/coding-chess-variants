package testRules

import coordinates.Coordinate2D
import gameMoves.GameMove2D
import gameTypes.chess.StandardChess
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.*

class CheckmateTest {

    val mockStandardChess = spyk<StandardChess>()
    val board = mockStandardChess.board

    val player1 = mockStandardChess.players[0]
    val player2 = mockStandardChess.players[1]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun turn4CheckmateTest() {
        mockStandardChess.initGame()

        val turn4Checkmate: List<GameMove2D.BasicGameMove> = listOf(
            GameMove2D.BasicGameMove(Coordinate2D(4, 1), Coordinate2D(4, 3), StandardWhitePawn(player1), player1),
            GameMove2D.BasicGameMove(Coordinate2D(4, 6), Coordinate2D(4, 4), StandardBlackPawn(player2), player2),
            GameMove2D.BasicGameMove(Coordinate2D(5, 0), Coordinate2D(2, 3), Bishop(player1), player1),
            GameMove2D.BasicGameMove(Coordinate2D(1, 7), Coordinate2D(2, 5), Knight(player2), player2),
            GameMove2D.BasicGameMove(Coordinate2D(3, 0), Coordinate2D(7, 4), Queen(player1), player1),
            GameMove2D.BasicGameMove(Coordinate2D(6, 7), Coordinate2D(5, 5), Knight(player2), player2),
            GameMove2D.BasicGameMove(
                Coordinate2D(7, 4), Coordinate2D(5, 6), Queen(player1), player1,
                board.getPiece(
                    Coordinate2D(5, 6)
                ),
                null, true
            )
        )

        for (move in turn4Checkmate) {
            mockStandardChess.makeMove(move)
        }

        mockStandardChess.getValidMoves(player2)

        assertTrue(mockStandardChess.checkmate)
    }

    @Test
    fun bishopsOnlyCheckmate() {
        board.addPiece(Coordinate2D(6, 5), King(player1))
        board.addPiece(Coordinate2D(4, 5), Bishop(player1))
        board.addPiece(Coordinate2D(4, 4), Bishop(player1))
        board.addPiece(Coordinate2D(7, 7), King(player2))

        mockStandardChess.getValidMoves(player2)

        assertTrue(mockStandardChess.checkmate)
    }
}
