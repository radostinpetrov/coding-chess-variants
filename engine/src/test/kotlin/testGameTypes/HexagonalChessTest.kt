package testGameTypes

import boards.BoardHex
import coordinates.Coordinate2D
import gameTypes.hex.GameTypeHex
import gameTypes.hex.HexagonalChess
import io.mockk.MockKAnnotations
import io.mockk.spyk
import moveGenerators.MoveGeneratorHex
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.hex.*
import testPerft.PerftUtility

class HexagonalChessTest {
    private var mockHexagonalChess = spyk<HexagonalChess>()

    private val board = mockHexagonalChess.board

    val player1 = mockHexagonalChess.players[0]
    val player2 = mockHexagonalChess.players[1]

    val perft = PerftUtility<GameTypeHex, BoardHex, MoveGeneratorHex, PieceHex, Coordinate2D>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun boardIsInitialized() {

        val initPiecesTest = listOf(
            // Pawns
            Pair(HexWhitePawn(player1), Coordinate2D(1,4)),
            Pair(HexWhitePawn(player1), Coordinate2D(2,5)),
            Pair(HexWhitePawn(player1), Coordinate2D(3,6)),
            Pair(HexWhitePawn(player1), Coordinate2D(4,7)),
            Pair(HexWhitePawn(player1), Coordinate2D(5,8)),
            Pair(HexWhitePawn(player1), Coordinate2D(6,7)),
            Pair(HexWhitePawn(player1), Coordinate2D(7,6)),
            Pair(HexWhitePawn(player1), Coordinate2D(8,5)),
            Pair(HexWhitePawn(player1), Coordinate2D(9,4)),

            Pair(HexBlackPawn(player2), Coordinate2D(1,16)),
            Pair(HexBlackPawn(player2), Coordinate2D(2,15)),
            Pair(HexBlackPawn(player2), Coordinate2D(3,14)),
            Pair(HexBlackPawn(player2), Coordinate2D(4,13)),
            Pair(HexBlackPawn(player2), Coordinate2D(5,12)),
            Pair(HexBlackPawn(player2), Coordinate2D(6,13)),
            Pair(HexBlackPawn(player2), Coordinate2D(7,14)),
            Pair(HexBlackPawn(player2), Coordinate2D(8,15)),
            Pair(HexBlackPawn(player2), Coordinate2D(9,16)),

            // Rooks
            Pair(HexRook(player1), Coordinate2D(2, 3)),
            Pair(HexRook(player1), Coordinate2D(8, 3)),
            Pair(HexRook(player2), Coordinate2D(2, 17)),
            Pair(HexRook(player2), Coordinate2D(8, 17)),

            // Knights
            Pair(HexKnight(player1), Coordinate2D(3, 2)),
            Pair(HexKnight(player1), Coordinate2D(7, 2)),
            Pair(HexKnight(player2), Coordinate2D(3, 18)),
            Pair(HexKnight(player2), Coordinate2D(7, 18)),

            // Bishops
            Pair(HexBishop(player1), Coordinate2D(5, 0)),
            Pair(HexBishop(player1), Coordinate2D(5, 2)),
            Pair(HexBishop(player1), Coordinate2D(5, 4)),
            Pair(HexBishop(player2), Coordinate2D(5, 20)),
            Pair(HexBishop(player2), Coordinate2D(5, 18)),
            Pair(HexBishop(player2), Coordinate2D(5, 16)),

            // Queens
            Pair(HexQueen(player1), Coordinate2D(4, 1)),
            Pair(HexQueen(player2), Coordinate2D(4, 19)),

            // Kings
            Pair(HexKing(player1), Coordinate2D(6, 1)),
            Pair(HexKing(player2), Coordinate2D(6, 19))
        )

        mockHexagonalChess.initGame()
        val initPieces = board.getPieces()
        Assertions.assertTrue(initPieces.containsAll(initPiecesTest))
        Assertions.assertEquals(initPieces.size, initPiecesTest.size)
    }

    @Test
    fun testHexagonalInitialPositionsWithDepth1() {
        perft.testSimple(HexagonalChess(), 1, 32)
    }

    @Test
    fun testHexagonalInitialPositionsWithDepth2() {
        perft.testSimple(HexagonalChess(), 2, 1012)
    }

    @Test
    fun testHexagonalInitialPositionsWithDepth3() {
        perft.testSimple(HexagonalChess(), 3, 29697)
    }
}