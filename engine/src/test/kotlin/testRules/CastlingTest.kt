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

class CastlingTest {

    val mockStandardChess = spyk<StandardChess>()

    val mockHumanPlayer1 = mockk<Player>()
    val mockHumanPlayer2 = mockk<Player>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun castleRightTest() {
        // mockStandardChess.addPlayer(mockHumanPlayer1)
        // mockStandardChess.addPlayer(mockHumanPlayer2)
        mockStandardChess.initGame()

        val initMoves: List<GameMove2D.BasicGameMove> = listOf(
            GameMove2D.BasicGameMove(Coordinate2D(4, 1), Coordinate2D(4, 3), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1),
            GameMove2D.BasicGameMove(Coordinate2D(0, 6), Coordinate2D(0, 5), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2),
            GameMove2D.BasicGameMove(Coordinate2D(5, 0), Coordinate2D(4, 1), Bishop(mockHumanPlayer1), mockHumanPlayer1),
            GameMove2D.BasicGameMove(Coordinate2D(1, 6), Coordinate2D(1, 5), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2),
            GameMove2D.BasicGameMove(Coordinate2D(6, 0), Coordinate2D(5, 2), Knight(mockHumanPlayer1), mockHumanPlayer1),
            GameMove2D.BasicGameMove(Coordinate2D(2, 6), Coordinate2D(2, 5), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2),
        )

        for (move in initMoves) {
            mockStandardChess.makeMove(move)
        }

        val moves = mockStandardChess.getValidMoves(mockHumanPlayer1)
        val castleMove = GameMove2D.CompositeGameMove(
            gameMoves = listOf(
                GameMove2D.BasicGameMove(from = Coordinate2D(4, 0), to = Coordinate2D(5, 0), pieceMoved = King(player = mockHumanPlayer1), player = mockHumanPlayer1, pieceCaptured = null, piecePromotedTo = null, checkForCheck = true),
                GameMove2D.BasicGameMove(from = Coordinate2D(5, 0), to = Coordinate2D(6, 0), pieceMoved = King(player = mockHumanPlayer1), player = mockHumanPlayer1, pieceCaptured = null, piecePromotedTo = null, checkForCheck = true),
                GameMove2D.BasicGameMove(from = Coordinate2D(7, 0), to = Coordinate2D(5, 0), pieceMoved = Rook(player = mockHumanPlayer1), player = mockHumanPlayer1, pieceCaptured = null, piecePromotedTo = null, checkForCheck = true)
            ),
            player = mockHumanPlayer1
        )
        assertTrue(moves.contains(castleMove))
    }

    @Test
    fun castleLeftTest() {
        // mockStandardChess.addPlayer(mockHumanPlayer1)
        // mockStandardChess.addPlayer(mockHumanPlayer2)
        mockStandardChess.initGame()

        val initMoves: List<GameMove2D.BasicGameMove> = listOf(
            GameMove2D.BasicGameMove(Coordinate2D(3, 1), Coordinate2D(3, 3), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1),
            GameMove2D.BasicGameMove(Coordinate2D(0, 6), Coordinate2D(0, 5), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2),
            GameMove2D.BasicGameMove(Coordinate2D(3, 0), Coordinate2D(3, 2), Queen(mockHumanPlayer1), mockHumanPlayer1),
            GameMove2D.BasicGameMove(Coordinate2D(1, 6), Coordinate2D(1, 5), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2),
            GameMove2D.BasicGameMove(Coordinate2D(2, 0), Coordinate2D(3, 1), Bishop(mockHumanPlayer1), mockHumanPlayer1),
            GameMove2D.BasicGameMove(Coordinate2D(2, 6), Coordinate2D(2, 5), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2),
            GameMove2D.BasicGameMove(Coordinate2D(1, 0), Coordinate2D(2, 2), Knight(mockHumanPlayer1), mockHumanPlayer1),
            GameMove2D.BasicGameMove(Coordinate2D(3, 6), Coordinate2D(3, 5), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2)
        )

        for (move in initMoves) {
            mockStandardChess.makeMove(move)
        }

        val moves = mockStandardChess.getValidMoves(mockHumanPlayer1)
        val castleMove = GameMove2D.CompositeGameMove(
            gameMoves = listOf(
                GameMove2D.BasicGameMove(from = Coordinate2D(4, 0), to = Coordinate2D(3, 0), pieceMoved = King(player = mockHumanPlayer1), player = mockHumanPlayer1, pieceCaptured = null, piecePromotedTo = null, checkForCheck = true),
                GameMove2D.BasicGameMove(from = Coordinate2D(3, 0), to = Coordinate2D(2, 0), pieceMoved = King(player = mockHumanPlayer1), player = mockHumanPlayer1, pieceCaptured = null, piecePromotedTo = null, checkForCheck = true),
                GameMove2D.BasicGameMove(from = Coordinate2D(0, 0), to = Coordinate2D(3, 0), pieceMoved = Rook(player = mockHumanPlayer1), player = mockHumanPlayer1, pieceCaptured = null, piecePromotedTo = null, checkForCheck = true)
            ),
            player = mockHumanPlayer1
        )
        assertTrue(moves.contains(castleMove))
    }
}
