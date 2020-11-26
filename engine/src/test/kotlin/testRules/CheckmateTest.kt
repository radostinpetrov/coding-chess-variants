package testRules

import io.mockk.mockk
import io.mockk.spyk
import main.kotlin.Coordinate
import main.kotlin.Game
import main.kotlin.GameMove
import main.kotlin.gameTypes.chess.StandardChess
import main.kotlin.pieces.chess.*
import main.kotlin.players.HumanPlayer
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CheckmateTest {

    val mockStandardChess = spyk<StandardChess>()
    val board = mockStandardChess.board

    val mockHumanPlayer1 = mockk<HumanPlayer>()
    val mockHumanPlayer2 = mockk<HumanPlayer>()

    @Test
    fun turn4CheckmateTest() {
        mockStandardChess.addPlayer(mockHumanPlayer1)
        mockStandardChess.addPlayer(mockHumanPlayer2)
        mockStandardChess.initGame()

        val move1 = GameMove.BasicGameMove(Coordinate(4,1), Coordinate(4, 3), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1)
        val move2 = GameMove.BasicGameMove(Coordinate(4,6), Coordinate(4, 4), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2)
        val move3 = GameMove.BasicGameMove(Coordinate(5,0), Coordinate(2, 3), Bishop(mockHumanPlayer1), mockHumanPlayer1)
        val move4 = GameMove.BasicGameMove(Coordinate(1, 7), Coordinate(2,5), Knight(mockHumanPlayer2), mockHumanPlayer2)
        val move5 = GameMove.BasicGameMove(Coordinate(3,0), Coordinate(7,4), Queen(mockHumanPlayer1), mockHumanPlayer1)
        val move6 = GameMove.BasicGameMove(Coordinate(6,7), Coordinate(5,5), Knight(mockHumanPlayer2), mockHumanPlayer2)
        val move7 = GameMove.BasicGameMove(Coordinate(7,4), Coordinate(5,6), Queen(mockHumanPlayer1), mockHumanPlayer1, board.getPiece(Coordinate(5,6)), null, true)

        mockStandardChess.makeMove(move1)
        mockStandardChess.makeMove(move2)
        mockStandardChess.makeMove(move3)
        mockStandardChess.makeMove(move4)
        mockStandardChess.makeMove(move5)
        mockStandardChess.makeMove(move6)
        mockStandardChess.makeMove(move7)

        mockStandardChess.getValidMoves(mockHumanPlayer2)

        assertTrue(mockStandardChess.checkmate)
    }

}