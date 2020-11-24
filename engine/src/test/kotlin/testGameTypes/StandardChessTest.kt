package testGameTypes

import io.mockk.MockKAnnotations
import io.mockk.mockk
import Coordinate
import GameMove
import gameTypes.chess.AbstractChess
import gameTypes.chess.StandardChess
import players.Player
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.*


class StandardChessTest {
    private var mockStandardChess: AbstractChess = StandardChess()

    private val board = mockStandardChess.board

    // val mockStandardChess = mockk<StandardChess>()
    val mockHumanPlayer1 = mockk<Player>()
    val mockHumanPlayer2 = mockk<Player>()
    // val mockGameMove = mockk<GameMove>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun boardIsInitialized() {

        val initPiecesTest = listOf(

            // Pawns
            Pair(StandardWhitePawn(mockHumanPlayer1), Coordinate(0, 1)),
            Pair(StandardWhitePawn(mockHumanPlayer1), Coordinate(1, 1)),
            Pair(StandardWhitePawn(mockHumanPlayer1), Coordinate(2, 1)),
            Pair(StandardWhitePawn(mockHumanPlayer1), Coordinate(3, 1)),
            Pair(StandardWhitePawn(mockHumanPlayer1), Coordinate(4, 1)),
            Pair(StandardWhitePawn(mockHumanPlayer1), Coordinate(5, 1)),
            Pair(StandardWhitePawn(mockHumanPlayer1), Coordinate(6, 1)),
            Pair(StandardWhitePawn(mockHumanPlayer1), Coordinate(7, 1)),
            Pair(StandardBlackPawn(mockHumanPlayer2), Coordinate(0, 6)),
            Pair(StandardBlackPawn(mockHumanPlayer2), Coordinate(1, 6)),
            Pair(StandardBlackPawn(mockHumanPlayer2), Coordinate(2, 6)),
            Pair(StandardBlackPawn(mockHumanPlayer2), Coordinate(3, 6)),
            Pair(StandardBlackPawn(mockHumanPlayer2), Coordinate(4, 6)),
            Pair(StandardBlackPawn(mockHumanPlayer2), Coordinate(5, 6)),
            Pair(StandardBlackPawn(mockHumanPlayer2), Coordinate(6, 6)),
            Pair(StandardBlackPawn(mockHumanPlayer2), Coordinate(7, 6)),
            // Rooks
            Pair(Rook(mockHumanPlayer1), Coordinate(0, 0)),
            Pair(Rook(mockHumanPlayer1), Coordinate(7, 0)),
            Pair(Rook(mockHumanPlayer2), Coordinate(0, 7)),
            Pair(Rook(mockHumanPlayer2), Coordinate(7, 7)),
            // Knights
            Pair(Knight(mockHumanPlayer1), Coordinate(1, 0)),
            Pair(Knight(mockHumanPlayer1), Coordinate(6, 0)),
            Pair(Knight(mockHumanPlayer2), Coordinate(1, 7)),
            Pair(Knight(mockHumanPlayer2), Coordinate(6, 7)),
            // Bishops
            Pair(Bishop(mockHumanPlayer1), Coordinate(2, 0)),
            Pair(Bishop(mockHumanPlayer1), Coordinate(5, 0)),
            Pair(Bishop(mockHumanPlayer2), Coordinate(2, 7)),
            Pair(Bishop(mockHumanPlayer2), Coordinate(5, 7)),
            // Queens
            Pair(Queen(mockHumanPlayer1), Coordinate(3, 0)),
            Pair(Queen(mockHumanPlayer2), Coordinate(3, 7)),
            // Kings
            Pair(King(mockHumanPlayer1), Coordinate(4, 0)),
            Pair(King(mockHumanPlayer2), Coordinate(4, 7))
        )
        mockStandardChess.addPlayer(mockHumanPlayer1)
        mockStandardChess.addPlayer(mockHumanPlayer2)
        mockStandardChess.initGame()
        val initPieces = board.getPieces()
        assertTrue(initPieces.containsAll(initPiecesTest))
    }

    @Test
    fun fromCoordinateIsEmptyAfterMove() {
        board.addPiece(Coordinate(0, 0), StandardWhitePawn(mockHumanPlayer1))
        val gameMove = GameMove.BasicGameMove(
            Coordinate(0, 0),
            Coordinate(1, 0), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1)
        mockStandardChess.makeMove(gameMove)
        assertTrue(board.getPiece(gameMove.from) == null)
    }

    @Test
    fun toCoordinateIsNewPiece() {
        board.addPiece(Coordinate(0, 0), StandardWhitePawn(mockHumanPlayer1))
        val gameMove = GameMove.BasicGameMove(
            Coordinate(0, 0),
            Coordinate(1, 0), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1)
        mockStandardChess.makeMove(gameMove)
        assertTrue(board.getPiece(gameMove.to) == gameMove.pieceMoved)
        if (gameMove.pieceCaptured != null) {
            assertFalse(board.getPiece(gameMove.to) == gameMove.pieceCaptured)
        }
    }
}
