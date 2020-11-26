package testRules

import Coordinate
import GameMove
import gameTypes.chess.StandardChess
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import pieces.chess.*
import players.Player

class CheckmateTest {

    val mockStandardChess = spyk<StandardChess>()
    val board = mockStandardChess.board

    val mockHumanPlayer1 = mockk<Player>()
    val mockHumanPlayer2 = mockk<Player>()

    @Test
    fun turn4CheckmateTest() {
        mockStandardChess.addPlayer(mockHumanPlayer1)
        mockStandardChess.addPlayer(mockHumanPlayer2)
        mockStandardChess.initGame()

        val turn4Checkmate: List<GameMove.BasicGameMove> = listOf(
            GameMove.BasicGameMove(Coordinate(4, 1), Coordinate(4, 3), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1),
            GameMove.BasicGameMove(Coordinate(4, 6), Coordinate(4, 4), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2),
            GameMove.BasicGameMove(Coordinate(5, 0), Coordinate(2, 3), Bishop(mockHumanPlayer1), mockHumanPlayer1),
            GameMove.BasicGameMove(Coordinate(1, 7), Coordinate(2, 5), Knight(mockHumanPlayer2), mockHumanPlayer2),
            GameMove.BasicGameMove(Coordinate(3, 0), Coordinate(7, 4), Queen(mockHumanPlayer1), mockHumanPlayer1),
            GameMove.BasicGameMove(Coordinate(6, 7), Coordinate(5, 5), Knight(mockHumanPlayer2), mockHumanPlayer2),
            GameMove.BasicGameMove(Coordinate(7, 4), Coordinate(5, 6), Queen(mockHumanPlayer1), mockHumanPlayer1, board.getPiece(Coordinate(5, 6)), null, true)
        )

        for (move in turn4Checkmate) {
            mockStandardChess.makeMove(move)
        }

        mockStandardChess.getValidMoves(mockHumanPlayer2)

        assertTrue(mockStandardChess.checkmate)
    }

    @Test
    fun bishopsOnlyCheckmate() {
        mockStandardChess.addPlayer(mockHumanPlayer1)
        mockStandardChess.addPlayer(mockHumanPlayer2)
        board.addPiece(Coordinate(6,5), King(mockHumanPlayer1))
        board.addPiece(Coordinate(4, 5), Bishop(mockHumanPlayer1))
        board.addPiece(Coordinate(4, 4), Bishop(mockHumanPlayer1))
        board.addPiece(Coordinate(7,7), King(mockHumanPlayer2))

        mockStandardChess.getValidMoves(mockHumanPlayer2)

        assertTrue(mockStandardChess.checkmate)
    }
}
