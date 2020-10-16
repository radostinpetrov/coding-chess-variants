package gameTypesTest

import Coordinate
import GameMove
import gameTypes.StandardChess
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.*
import players.HumanPlayer

class StandardChessTest {
    // private var mockStandardChess: StandardChess = StandardChess()
    // private var mockHumanPlayer1: HumanPlayer = HumanPlayer()
    // private var mockHumanPlayer2: HumanPlayer = HumanPlayer()
    // private var mockGameMove: GameMove =

    @MockK
    private lateinit var mockStandardChess: StandardChess

    @MockK
    private lateinit var mockHumanPlayer1: HumanPlayer

    @MockK
    private lateinit var mockHumanPlayer2: HumanPlayer

    @MockK
    private lateinit var mockGameMove: GameMove

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun boardIsInitialized() {
        val initPiecesTest = listOf(
            // Pawns
            Pair(WhitePawn(mockHumanPlayer1), Coordinate(0, 1)),
            Pair(WhitePawn(mockHumanPlayer1), Coordinate(1, 1)),
            Pair(WhitePawn(mockHumanPlayer1), Coordinate(2, 1)),
            Pair(WhitePawn(mockHumanPlayer1), Coordinate(3, 1)),
            Pair(WhitePawn(mockHumanPlayer1), Coordinate(4, 1)),
            Pair(WhitePawn(mockHumanPlayer1), Coordinate(5, 1)),
            Pair(WhitePawn(mockHumanPlayer1), Coordinate(6, 1)),
            Pair(WhitePawn(mockHumanPlayer1), Coordinate(7, 1)),
            Pair(BlackPawn(mockHumanPlayer2), Coordinate(0, 6)),
            Pair(BlackPawn(mockHumanPlayer2), Coordinate(1, 6)),
            Pair(BlackPawn(mockHumanPlayer2), Coordinate(2, 6)),
            Pair(BlackPawn(mockHumanPlayer2), Coordinate(3, 6)),
            Pair(BlackPawn(mockHumanPlayer2), Coordinate(4, 6)),
            Pair(BlackPawn(mockHumanPlayer2), Coordinate(5, 6)),
            Pair(BlackPawn(mockHumanPlayer2), Coordinate(6, 6)),
            Pair(BlackPawn(mockHumanPlayer2), Coordinate(7, 6)),
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
        mockStandardChess.initGame()
        val initPieces = mockStandardChess.board.getPieces()
        assertTrue(initPiecesTest == initPieces)
    }

    @Test
    fun fromCoordinateIsEmptyAfterMove() {
        mockStandardChess.makeMove(mockGameMove)
        assertTrue(mockStandardChess.board.getPiece(mockGameMove.from) == null)
    }

    @Test
    fun toCoordinateIsNewPiece() {
        mockStandardChess.makeMove(mockGameMove)
        assertTrue(mockStandardChess.board.getPiece(mockGameMove.to) == mockGameMove.pieceMoved)
        if (mockGameMove.pieceCaptured != null) {
            assertFalse(mockStandardChess.board.getPiece(mockGameMove.to) == mockGameMove.pieceCaptured)
        }
    }
}
