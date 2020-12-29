package testGameTypes

import Coordinate
import gameTypes.chess.CapablancaChess
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.*
import players.Player

class CapablancaChessTest {
    private var mockCapablancaChess = spyk<CapablancaChess>()

    private val board = mockCapablancaChess.board

    val mockHumanPlayer1 = mockk<Player>()
    val mockHumanPlayer2 = mockk<Player>()

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
            Pair(StandardWhitePawn(mockHumanPlayer1), Coordinate(8, 1)),
            Pair(StandardWhitePawn(mockHumanPlayer1), Coordinate(9, 1)),

            Pair(StandardBlackPawn(mockHumanPlayer2), Coordinate(0, 6)),
            Pair(StandardBlackPawn(mockHumanPlayer2), Coordinate(1, 6)),
            Pair(StandardBlackPawn(mockHumanPlayer2), Coordinate(2, 6)),
            Pair(StandardBlackPawn(mockHumanPlayer2), Coordinate(3, 6)),
            Pair(StandardBlackPawn(mockHumanPlayer2), Coordinate(4, 6)),
            Pair(StandardBlackPawn(mockHumanPlayer2), Coordinate(5, 6)),
            Pair(StandardBlackPawn(mockHumanPlayer2), Coordinate(6, 6)),
            Pair(StandardBlackPawn(mockHumanPlayer2), Coordinate(7, 6)),
            Pair(StandardBlackPawn(mockHumanPlayer2), Coordinate(8, 6)),
            Pair(StandardBlackPawn(mockHumanPlayer2), Coordinate(9, 6)),
            // Rooks
            Pair(Rook(mockHumanPlayer1), Coordinate(0, 0)),
            Pair(Rook(mockHumanPlayer1), Coordinate(9, 0)),
            Pair(Rook(mockHumanPlayer2), Coordinate(0, 7)),
            Pair(Rook(mockHumanPlayer2), Coordinate(9, 7)),
            // Knights
            Pair(Knight(mockHumanPlayer1), Coordinate(1, 0)),
            Pair(Knight(mockHumanPlayer1), Coordinate(8, 0)),
            Pair(Knight(mockHumanPlayer2), Coordinate(1, 7)),
            Pair(Knight(mockHumanPlayer2), Coordinate(8, 7)),
            // Cardinals
            Pair(Cardinal(mockHumanPlayer1), Coordinate(2, 0)),
            Pair(Cardinal(mockHumanPlayer2), Coordinate(2, 7)),
            // Marshals
            Pair(Marshal(mockHumanPlayer1), Coordinate(7, 0)),
            Pair(Marshal(mockHumanPlayer2), Coordinate(7, 7)),
            // Bishops
            Pair(Bishop(mockHumanPlayer1), Coordinate(3, 0)),
            Pair(Bishop(mockHumanPlayer1), Coordinate(6, 0)),
            Pair(Bishop(mockHumanPlayer2), Coordinate(3, 7)),
            Pair(Bishop(mockHumanPlayer2), Coordinate(6, 7)),
            // Queens
            Pair(Queen(mockHumanPlayer1), Coordinate(4, 0)),
            Pair(Queen(mockHumanPlayer2), Coordinate(4, 7)),
            // Kings
            Pair(King(mockHumanPlayer1), Coordinate(5, 0)),
            Pair(King(mockHumanPlayer2), Coordinate(5, 7))
        )
        // mockCapablancaChess.addPlayer(mockHumanPlayer1)
        // mockCapablancaChess.addPlayer(mockHumanPlayer2)
        mockCapablancaChess.initGame()
        val initPieces = board.getPieces()
        Assertions.assertTrue(initPieces.containsAll(initPiecesTest))
        Assertions.assertEquals(initPieces.size, initPiecesTest.size)
    }
}
