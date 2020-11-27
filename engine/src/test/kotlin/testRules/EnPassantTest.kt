package testRules

import Coordinate
import GameMove
import gameTypes.chess.StandardChess
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.StandardBlackPawn
import pieces.chess.StandardWhitePawn
import players.Player

class EnPassantTest {

    val mockStandardChess = spyk<StandardChess>()
    val board = mockStandardChess.board

    val mockHumanPlayer1 = mockk<Player>()
    val mockHumanPlayer2 = mockk<Player>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun simplePawnEnPassant() {
        mockStandardChess.addPlayer(mockHumanPlayer1)
        mockStandardChess.addPlayer(mockHumanPlayer2)
        mockStandardChess.initGame()

        val enPassantMove: List<GameMove.BasicGameMove> = listOf(
            GameMove.BasicGameMove(Coordinate(4, 4), Coordinate(3, 4), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1, board.getPiece(Coordinate(3, 4))),
            GameMove.BasicGameMove(Coordinate(3, 4), Coordinate(3, 5), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1)
        )

        val initMoves: List<GameMove> = listOf(
            GameMove.BasicGameMove(Coordinate(4, 1), Coordinate(4, 3), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1),
            GameMove.BasicGameMove(Coordinate(3, 6), Coordinate(3, 4), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2),
            GameMove.BasicGameMove(Coordinate(4, 3), Coordinate(4, 4), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1),
            GameMove.BasicGameMove(Coordinate(1, 6), Coordinate(1, 4), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2),
            GameMove.CompositeGameMove(enPassantMove, mockHumanPlayer1)
        )

        for (move in initMoves) {
            mockStandardChess.makeMove(move)
        }

        assert(board.getPiece(Coordinate(3, 5)) is StandardWhitePawn)
        assert(board.getPiece(Coordinate(3, 4)) == null)
    }
}
