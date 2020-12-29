package testRules

import coordinates.Coordinate2D
import gameMoves.GameMove2D
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

        val enPassantMove: List<GameMove2D.BasicGameMove> = listOf(
            GameMove2D.BasicGameMove(
                Coordinate2D(4, 4), Coordinate2D(3, 4), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1, board.getPiece(
                    Coordinate2D(3, 4)
                )),
            GameMove2D.BasicGameMove(Coordinate2D(3, 4), Coordinate2D(3, 5), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1)
        )

        val initMoves: List<GameMove2D> = listOf(
            GameMove2D.BasicGameMove(Coordinate2D(4, 1), Coordinate2D(4, 3), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1),
            GameMove2D.BasicGameMove(Coordinate2D(3, 6), Coordinate2D(3, 4), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2),
            GameMove2D.BasicGameMove(Coordinate2D(4, 3), Coordinate2D(4, 4), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1),
            GameMove2D.BasicGameMove(Coordinate2D(1, 6), Coordinate2D(1, 4), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2),
            GameMove2D.CompositeGameMove(enPassantMove, mockHumanPlayer1)
        )

        for (move in initMoves) {
            mockStandardChess.makeMove(move)
        }

        assert(board.getPiece(Coordinate2D(3, 5)) is StandardWhitePawn)
        assert(board.getPiece(Coordinate2D(3, 4)) == null)
    }
}
