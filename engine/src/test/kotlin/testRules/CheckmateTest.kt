package testRules

import coordinates.Coordinate2D
import gameMoves.GameMove2D
import gameTypes.chess.StandardChess
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.*
import players.Player

class CheckmateTest {

    val mockStandardChess = spyk<StandardChess>()
    val board = mockStandardChess.board

    val mockHumanPlayer1 = mockk<Player>()
    val mockHumanPlayer2 = mockk<Player>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun turn4CheckmateTest() {
        // mockStandardChess.addPlayer(mockHumanPlayer1)
        // mockStandardChess.addPlayer(mockHumanPlayer2)
        mockStandardChess.initGame()

        val turn4Checkmate: List<GameMove2D.BasicGameMove> = listOf(
            GameMove2D.BasicGameMove(Coordinate2D(4, 1), Coordinate2D(4, 3), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1),
            GameMove2D.BasicGameMove(Coordinate2D(4, 6), Coordinate2D(4, 4), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2),
            GameMove2D.BasicGameMove(Coordinate2D(5, 0), Coordinate2D(2, 3), Bishop(mockHumanPlayer1), mockHumanPlayer1),
            GameMove2D.BasicGameMove(Coordinate2D(1, 7), Coordinate2D(2, 5), Knight(mockHumanPlayer2), mockHumanPlayer2),
            GameMove2D.BasicGameMove(Coordinate2D(3, 0), Coordinate2D(7, 4), Queen(mockHumanPlayer1), mockHumanPlayer1),
            GameMove2D.BasicGameMove(Coordinate2D(6, 7), Coordinate2D(5, 5), Knight(mockHumanPlayer2), mockHumanPlayer2),
            GameMove2D.BasicGameMove(
                Coordinate2D(7, 4), Coordinate2D(5, 6), Queen(mockHumanPlayer1), mockHumanPlayer1, board.getPiece(
                    Coordinate2D(5, 6)
                ), null, true)
        )

        for (move in turn4Checkmate) {
            mockStandardChess.makeMove(move)
        }

        mockStandardChess.getValidMoves(mockHumanPlayer2)

        assertTrue(mockStandardChess.checkmate)
    }

    @Test
    fun bishopsOnlyCheckmate() {
//        mockStandardChess.addPlayer(mockHumanPlayer1)
//        mockStandardChess.addPlayer(mockHumanPlayer2)
        board.addPiece(Coordinate2D(6,5), King(mockHumanPlayer1))
        board.addPiece(Coordinate2D(4, 5), Bishop(mockHumanPlayer1))
        board.addPiece(Coordinate2D(4, 4), Bishop(mockHumanPlayer1))
        board.addPiece(Coordinate2D(7,7), King(mockHumanPlayer2))

        mockStandardChess.getValidMoves(mockHumanPlayer2)

        assertTrue(mockStandardChess.checkmate)
    }
}
