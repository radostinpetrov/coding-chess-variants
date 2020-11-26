package testRules

import Coordinate
import gameTypes.chess.StandardChess
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import pieces.chess.*
import players.Player

class StalemateTest {
    val mockStandardChess = spyk<StandardChess>()
    val board = mockStandardChess.board

    val mockHumanPlayer1 = mockk<Player>()
    val mockHumanPlayer2 = mockk<Player>()

    @Test
    fun quickStalemateTest() {
        mockStandardChess.addPlayer(mockHumanPlayer1)
        mockStandardChess.addPlayer(mockHumanPlayer2)
        mockStandardChess.initGame()

        val turn10Stalemate: List<GameMove.BasicGameMove> = listOf(
            GameMove.BasicGameMove(Coordinate(4, 1), Coordinate(4, 2), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1),
            GameMove.BasicGameMove(Coordinate(0, 6), Coordinate(0, 4), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2),
            GameMove.BasicGameMove(Coordinate(3, 0), Coordinate(7, 4), Queen(mockHumanPlayer1), mockHumanPlayer1),
            GameMove.BasicGameMove(Coordinate(0, 7), Coordinate(0, 5), Rook(mockHumanPlayer2), mockHumanPlayer2),
            GameMove.BasicGameMove(Coordinate(7, 4), Coordinate(0, 4), Queen(mockHumanPlayer1), mockHumanPlayer1),
            GameMove.BasicGameMove(Coordinate(7, 6), Coordinate(7, 4), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2),
            GameMove.BasicGameMove(Coordinate(0, 4), Coordinate(2, 6), Queen(mockHumanPlayer1), mockHumanPlayer1, board.getPiece(Coordinate(2, 6))),
            GameMove.BasicGameMove(Coordinate(0, 5), Coordinate(7, 5), Rook(mockHumanPlayer2), mockHumanPlayer2, board.getPiece(Coordinate(7, 5))),
            GameMove.BasicGameMove(Coordinate(7, 1), Coordinate(7, 3), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1),
            GameMove.BasicGameMove(Coordinate(5, 6), Coordinate(5, 5), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2),
            GameMove.BasicGameMove(Coordinate(2, 6), Coordinate(3, 6), Queen(mockHumanPlayer1), mockHumanPlayer1),
            GameMove.BasicGameMove(Coordinate(4, 7), Coordinate(5, 6), King(mockHumanPlayer2), mockHumanPlayer2),
            GameMove.BasicGameMove(Coordinate(3, 6), Coordinate(1, 6), Queen(mockHumanPlayer1), mockHumanPlayer1, board.getPiece(Coordinate(1, 6))),
            GameMove.BasicGameMove(Coordinate(3, 7), Coordinate(3, 2), Queen(mockHumanPlayer2), mockHumanPlayer2),
            GameMove.BasicGameMove(Coordinate(1, 6), Coordinate(1, 7), Queen(mockHumanPlayer1), mockHumanPlayer1, board.getPiece(Coordinate(1, 7))),
            GameMove.BasicGameMove(Coordinate(3, 2), Coordinate(7, 6), Queen(mockHumanPlayer2), mockHumanPlayer2),
            GameMove.BasicGameMove(Coordinate(1, 7), Coordinate(2, 7), Queen(mockHumanPlayer1), mockHumanPlayer1, board.getPiece(Coordinate(2, 7))),
            GameMove.BasicGameMove(Coordinate(5, 6), Coordinate(6, 5), King(mockHumanPlayer2), mockHumanPlayer2),
            GameMove.BasicGameMove(Coordinate(2, 7), Coordinate(4, 5), Queen(mockHumanPlayer1), mockHumanPlayer1),
        )

        for (move in turn10Stalemate) {
            mockStandardChess.makeMove(move)
        }

        mockStandardChess.getValidMoves(mockHumanPlayer2)
        assertTrue(mockStandardChess.stalemate)
    }
}
