package testGameTypes

import coordinates.Coordinate2D
import gameTypes.chess.AntiChess
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.antichess.AntiChessBlackPawn
import pieces.antichess.AntiChessKing
import pieces.antichess.AntiChessWhitePawn
import pieces.chess.*
import testPerft.PerftUtility

class AntiChessTest {
    private var mockAntiChess = spyk<AntiChess>()

    private val board = mockAntiChess.board

    val player1 = mockAntiChess.players[0]
    val player2 = mockAntiChess.players[1]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun boardIsInitialized() {

        val initPiecesTest = listOf(
            // Pawns
            Pair(AntiChessWhitePawn(player1), Coordinate2D(0, 1)),
            Pair(AntiChessWhitePawn(player1), Coordinate2D(1, 1)),
            Pair(AntiChessWhitePawn(player1), Coordinate2D(2, 1)),
            Pair(AntiChessWhitePawn(player1), Coordinate2D(3, 1)),
            Pair(AntiChessWhitePawn(player1), Coordinate2D(4, 1)),
            Pair(AntiChessWhitePawn(player1), Coordinate2D(5, 1)),
            Pair(AntiChessWhitePawn(player1), Coordinate2D(6, 1)),
            Pair(AntiChessWhitePawn(player1), Coordinate2D(7, 1)),
            Pair(AntiChessBlackPawn(player2), Coordinate2D(0, 6)),
            Pair(AntiChessBlackPawn(player2), Coordinate2D(1, 6)),
            Pair(AntiChessBlackPawn(player2), Coordinate2D(2, 6)),
            Pair(AntiChessBlackPawn(player2), Coordinate2D(3, 6)),
            Pair(AntiChessBlackPawn(player2), Coordinate2D(4, 6)),
            Pair(AntiChessBlackPawn(player2), Coordinate2D(5, 6)),
            Pair(AntiChessBlackPawn(player2), Coordinate2D(6, 6)),
            Pair(AntiChessBlackPawn(player2), Coordinate2D(7, 6)),
            // Rooks
            Pair(Rook(player1), Coordinate2D(0, 0)),
            Pair(Rook(player1), Coordinate2D(7, 0)),
            Pair(Rook(player2), Coordinate2D(0, 7)),
            Pair(Rook(player2), Coordinate2D(7, 7)),
            // Knights
            Pair(Knight(player1), Coordinate2D(1, 0)),
            Pair(Knight(player1), Coordinate2D(6, 0)),
            Pair(Knight(player2), Coordinate2D(1, 7)),
            Pair(Knight(player2), Coordinate2D(6, 7)),
            // Bishops
            Pair(Bishop(player1), Coordinate2D(2, 0)),
            Pair(Bishop(player1), Coordinate2D(5, 0)),
            Pair(Bishop(player2), Coordinate2D(2, 7)),
            Pair(Bishop(player2), Coordinate2D(5, 7)),
            // Queens
            Pair(Queen(player1), Coordinate2D(3, 0)),
            Pair(Queen(player2), Coordinate2D(3, 7)),
            // Kings
            Pair(AntiChessKing(player1), Coordinate2D(4, 0)),
            Pair(AntiChessKing(player2), Coordinate2D(4, 7))
        )

        mockAntiChess.initGame()
        val initPieces = board.getPieces()
        Assertions.assertTrue(initPieces.containsAll(initPiecesTest))
        Assertions.assertEquals(initPieces.size, initPiecesTest.size)
    }

    @Test
    fun testAntiChessInitialPositionsWithDepth1() {
        PerftUtility.testSimple(AntiChess(), 1, 20)
    }

    @Test
    fun testAntiChessInitialPositionsWithDepth2() {
        PerftUtility.testSimple(AntiChess(), 2, 400)
    }

//    @Test
//    fun testAntiChessInitialPositionsWithDepth3() {
//        PerftUtility.testSimple(AntiChess(), 3, 8067)
//    }
//
//    @Test
//    fun testAntiChessInitialPositionsWithDepth4() {
//        PerftUtility.testSimple(AntiChess(), 4, 153299)
//    }
}
