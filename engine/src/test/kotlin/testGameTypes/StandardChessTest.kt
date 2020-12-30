package testGameTypes

import coordinates.Coordinate2D
import gameMoves.GameMove2D
import gameTypes.chess.StandardChess
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.*
import testPerft.PerftUtility
import testPerft.PerftUtility.test
import testPerft.PerftUtility.testSimple

class StandardChessTest {
    val mockStandardChess = spyk<StandardChess>()

    private val board = mockStandardChess.board

    val player1 = mockStandardChess.players[0]
    val player2 = mockStandardChess.players[1]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun boardIsInitialized() {

        val initPiecesTest = listOf(

            // Pawns
            Pair(StandardWhitePawn(player1), Coordinate2D(0, 1)),
            Pair(StandardWhitePawn(player1), Coordinate2D(1, 1)),
            Pair(StandardWhitePawn(player1), Coordinate2D(2, 1)),
            Pair(StandardWhitePawn(player1), Coordinate2D(3, 1)),
            Pair(StandardWhitePawn(player1), Coordinate2D(4, 1)),
            Pair(StandardWhitePawn(player1), Coordinate2D(5, 1)),
            Pair(StandardWhitePawn(player1), Coordinate2D(6, 1)),
            Pair(StandardWhitePawn(player1), Coordinate2D(7, 1)),
            Pair(StandardBlackPawn(player2), Coordinate2D(0, 6)),
            Pair(StandardBlackPawn(player2), Coordinate2D(1, 6)),
            Pair(StandardBlackPawn(player2), Coordinate2D(2, 6)),
            Pair(StandardBlackPawn(player2), Coordinate2D(3, 6)),
            Pair(StandardBlackPawn(player2), Coordinate2D(4, 6)),
            Pair(StandardBlackPawn(player2), Coordinate2D(5, 6)),
            Pair(StandardBlackPawn(player2), Coordinate2D(6, 6)),
            Pair(StandardBlackPawn(player2), Coordinate2D(7, 6)),
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
            Pair(King(player1), Coordinate2D(4, 0)),
            Pair(King(player2), Coordinate2D(4, 7))
        )

        mockStandardChess.initGame()
        val initPieces = board.getPieces()
        assertTrue(initPieces.containsAll(initPiecesTest))
        Assertions.assertEquals(initPieces.size, initPiecesTest.size)
    }

    @Test
    fun fromCoordinateIsEmptyAfterMove() {
        board.addPiece(Coordinate2D(0, 0), StandardWhitePawn(player1))
        val gameMove = GameMove2D.BasicGameMove(
            Coordinate2D(0, 0),
            Coordinate2D(1, 0), StandardWhitePawn(player1), player1
        )
        mockStandardChess.makeMove(gameMove)
        assertTrue(board.getPiece(gameMove.from) == null)
    }

    @Test
    fun toCoordinateIsNewPiece() {
        board.addPiece(Coordinate2D(0, 0), StandardWhitePawn(player1))
        val gameMove = GameMove2D.BasicGameMove(
            Coordinate2D(0, 0),
            Coordinate2D(1, 0), StandardWhitePawn(player1), player1
        )
        mockStandardChess.makeMove(gameMove)
        assertTrue(board.getPiece(gameMove.to) == gameMove.pieceMoved)
        if (gameMove.pieceCaptured != null) {
            assertFalse(board.getPiece(gameMove.to) == gameMove.pieceCaptured)
        }
    }

    @Test
    fun testStandardChessInitialPositionsWithDepth1() {
        test(StandardChess(), 1, PerftUtility.PerftData(20, 0, 0, 0))
    }

    @Test
    fun testStandardChessInitialPositionsWithDepth2() {
        test(StandardChess(), 2, PerftUtility.PerftData(400, 0, 0, 0))
    }

    @Test
    fun testStandardChessInitialPositionsWithDepth3() {
        test(StandardChess(), 3, PerftUtility.PerftData(8902, 34, 12, 0))
    }

    @Test
    fun testStandardChessInitialPositionsWithDepth4() {
        test(StandardChess(), 4, PerftUtility.PerftData(197281, 1576, 469, 8))
    }

    @Test
    fun testStandardChessInGamePosition1WithDepth1() {
        testSimple(StandardChess("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R"), 1, 48)
    }

    @Test
    fun testStandardChessInGamePosition1WithDepth2() {
        testSimple(StandardChess("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R"), 2, 2039)
    }

    @Test
    fun testStandardChessInGamePosition2WithDepth2() {
        testSimple(StandardChess("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8"), 2, 191)
    }
//    @Test
//    fun testStandardChessInitialPositionsWithDepth5() {
//        test(StandardChess(), 5, PerftUtility.PerftData(4865609, 82719, 27351, 347))
//    }
}
