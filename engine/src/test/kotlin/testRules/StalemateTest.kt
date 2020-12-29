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

class StalemateTest {
    val mockStandardChess = spyk<StandardChess>()
    val board = mockStandardChess.board

    val mockHumanPlayer1 = mockk<Player>()
    val mockHumanPlayer2 = mockk<Player>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun quickStalemateTest() {
        // mockStandardChess.addPlayer(mockHumanPlayer1)
        // mockStandardChess.addPlayer(mockHumanPlayer2)
        mockStandardChess.initGame()

        val turn10Stalemate: List<GameMove2D.BasicGameMove> = listOf(
            GameMove2D.BasicGameMove(Coordinate2D(4, 1), Coordinate2D(4, 2), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1),
            GameMove2D.BasicGameMove(Coordinate2D(0, 6), Coordinate2D(0, 4), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2),
            GameMove2D.BasicGameMove(Coordinate2D(3, 0), Coordinate2D(7, 4), Queen(mockHumanPlayer1), mockHumanPlayer1),
            GameMove2D.BasicGameMove(Coordinate2D(0, 7), Coordinate2D(0, 5), Rook(mockHumanPlayer2), mockHumanPlayer2),
            GameMove2D.BasicGameMove(Coordinate2D(7, 4), Coordinate2D(0, 4), Queen(mockHumanPlayer1), mockHumanPlayer1),
            GameMove2D.BasicGameMove(Coordinate2D(7, 6), Coordinate2D(7, 4), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2),
            GameMove2D.BasicGameMove(
                Coordinate2D(0, 4), Coordinate2D(2, 6), Queen(mockHumanPlayer1), mockHumanPlayer1, board.getPiece(
                    Coordinate2D(2, 6)
                )),
            GameMove2D.BasicGameMove(
                Coordinate2D(0, 5), Coordinate2D(7, 5), Rook(mockHumanPlayer2), mockHumanPlayer2, board.getPiece(
                    Coordinate2D(7, 5)
                )),
            GameMove2D.BasicGameMove(Coordinate2D(7, 1), Coordinate2D(7, 3), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1),
            GameMove2D.BasicGameMove(Coordinate2D(5, 6), Coordinate2D(5, 5), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2),
            GameMove2D.BasicGameMove(Coordinate2D(2, 6), Coordinate2D(3, 6), Queen(mockHumanPlayer1), mockHumanPlayer1),
            GameMove2D.BasicGameMove(Coordinate2D(4, 7), Coordinate2D(5, 6), King(mockHumanPlayer2), mockHumanPlayer2),
            GameMove2D.BasicGameMove(
                Coordinate2D(3, 6), Coordinate2D(1, 6), Queen(mockHumanPlayer1), mockHumanPlayer1, board.getPiece(
                    Coordinate2D(1, 6)
                )),
            GameMove2D.BasicGameMove(Coordinate2D(3, 7), Coordinate2D(3, 2), Queen(mockHumanPlayer2), mockHumanPlayer2),
            GameMove2D.BasicGameMove(
                Coordinate2D(1, 6), Coordinate2D(1, 7), Queen(mockHumanPlayer1), mockHumanPlayer1, board.getPiece(
                    Coordinate2D(1, 7)
                )),
            GameMove2D.BasicGameMove(Coordinate2D(3, 2), Coordinate2D(7, 6), Queen(mockHumanPlayer2), mockHumanPlayer2),
            GameMove2D.BasicGameMove(
                Coordinate2D(1, 7), Coordinate2D(2, 7), Queen(mockHumanPlayer1), mockHumanPlayer1, board.getPiece(
                    Coordinate2D(2, 7)
                )),
            GameMove2D.BasicGameMove(Coordinate2D(5, 6), Coordinate2D(6, 5), King(mockHumanPlayer2), mockHumanPlayer2),
            GameMove2D.BasicGameMove(Coordinate2D(2, 7), Coordinate2D(4, 5), Queen(mockHumanPlayer1), mockHumanPlayer1),
        )

        for (move in turn10Stalemate) {
            mockStandardChess.makeMove(move)
        }

        mockStandardChess.getValidMoves(mockHumanPlayer2)
        assertTrue(mockStandardChess.stalemate)
    }
}
