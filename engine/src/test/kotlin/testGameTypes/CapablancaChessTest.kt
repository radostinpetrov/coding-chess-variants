package testGameTypes

import coordinates.Coordinate2D
import gameTypes.chess.CapablancaChess
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.*
import testPerft.PerftUtility

class CapablancaChessTest {
    private var mockCapablancaChess = spyk<CapablancaChess>()

    private val board = mockCapablancaChess.board

    val player1 = mockCapablancaChess.players[0]
    val player2 = mockCapablancaChess.players[1]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun boardIsInitialized() {

        val initPiecesTest = listOf(
            // Pawns
            Pair(CapablancaWhitePawn(player1), Coordinate2D(0, 1)),
            Pair(CapablancaWhitePawn(player1), Coordinate2D(1, 1)),
            Pair(CapablancaWhitePawn(player1), Coordinate2D(2, 1)),
            Pair(CapablancaWhitePawn(player1), Coordinate2D(3, 1)),
            Pair(CapablancaWhitePawn(player1), Coordinate2D(4, 1)),
            Pair(CapablancaWhitePawn(player1), Coordinate2D(5, 1)),
            Pair(CapablancaWhitePawn(player1), Coordinate2D(6, 1)),
            Pair(CapablancaWhitePawn(player1), Coordinate2D(7, 1)),
            Pair(CapablancaWhitePawn(player1), Coordinate2D(8, 1)),
            Pair(CapablancaWhitePawn(player1), Coordinate2D(9, 1)),

            Pair(CapablancaBlackPawn(player2), Coordinate2D(0, 6)),
            Pair(CapablancaBlackPawn(player2), Coordinate2D(1, 6)),
            Pair(CapablancaBlackPawn(player2), Coordinate2D(2, 6)),
            Pair(CapablancaBlackPawn(player2), Coordinate2D(3, 6)),
            Pair(CapablancaBlackPawn(player2), Coordinate2D(4, 6)),
            Pair(CapablancaBlackPawn(player2), Coordinate2D(5, 6)),
            Pair(CapablancaBlackPawn(player2), Coordinate2D(6, 6)),
            Pair(CapablancaBlackPawn(player2), Coordinate2D(7, 6)),
            Pair(CapablancaBlackPawn(player2), Coordinate2D(8, 6)),
            Pair(CapablancaBlackPawn(player2), Coordinate2D(9, 6)),
            // Rooks
            Pair(Rook(player1), Coordinate2D(0, 0)),
            Pair(Rook(player1), Coordinate2D(9, 0)),
            Pair(Rook(player2), Coordinate2D(0, 7)),
            Pair(Rook(player2), Coordinate2D(9, 7)),
            // Knights
            Pair(Knight(player1), Coordinate2D(1, 0)),
            Pair(Knight(player1), Coordinate2D(8, 0)),
            Pair(Knight(player2), Coordinate2D(1, 7)),
            Pair(Knight(player2), Coordinate2D(8, 7)),
            // Cardinals
            Pair(Cardinal(player1), Coordinate2D(2, 0)),
            Pair(Cardinal(player2), Coordinate2D(2, 7)),
            // Marshals
            Pair(Marshal(player1), Coordinate2D(7, 0)),
            Pair(Marshal(player2), Coordinate2D(7, 7)),
            // Bishops
            Pair(Bishop(player1), Coordinate2D(3, 0)),
            Pair(Bishop(player1), Coordinate2D(6, 0)),
            Pair(Bishop(player2), Coordinate2D(3, 7)),
            Pair(Bishop(player2), Coordinate2D(6, 7)),
            // Queens
            Pair(Queen(player1), Coordinate2D(4, 0)),
            Pair(Queen(player2), Coordinate2D(4, 7)),
            // Kings
            Pair(King(player1), Coordinate2D(5, 0)),
            Pair(King(player2), Coordinate2D(5, 7))
        )

        mockCapablancaChess.initGame()
        val initPieces = board.getPieces()
        Assertions.assertTrue(initPieces.containsAll(initPiecesTest))
        Assertions.assertEquals(initPieces.size, initPiecesTest.size)
    }

    @Test
    fun testCapablancaChessInitialPositionsWithDepth1() {
        PerftUtility.testSimple(CapablancaChess(), 1, 28)
    }

    @Test
    fun testCapablancaChessInitialPositionsWithDepth2() {
        PerftUtility.testSimple(CapablancaChess(), 2, 784)
    }

//    @Test
//    fun testCapablancaChessInitialPositionsWithDepth3() {
//        PerftUtility.testSimple(CapablancaChess(), 3, 25228)
//    }

//    @Test
//    fun testCapablancaChessInitialPositionsWithDepth4() {
//        PerftUtility.testSimple(CapablancaChess(), 4, 805128)
//    }
}
