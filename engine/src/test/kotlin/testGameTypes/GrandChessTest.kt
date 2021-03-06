package testGameTypes

import boards.Board2D
import coordinates.Coordinate2D
import gameTypes.GameType2D
import gameTypes.chess.GrandChess
import io.mockk.MockKAnnotations
import io.mockk.spyk
import moveGenerators.MoveGenerator2D
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.Piece2D
import pieces.chess.*
import testPerft.PerftUtility

class GrandChessTest {
    private var mockGrandChess = spyk<GrandChess>()

    private val board = mockGrandChess.board

    val player1 = mockGrandChess.players[0]
    val player2 = mockGrandChess.players[1]

    val perft = PerftUtility<GameType2D, Board2D, MoveGenerator2D, Piece2D, Coordinate2D>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun boardIsInitialized() {

        val initPiecesTest = listOf(
            // Pawns
            Pair(GrandWhitePawn(player1), Coordinate2D(0, 2)),
            Pair(GrandWhitePawn(player1), Coordinate2D(1, 2)),
            Pair(GrandWhitePawn(player1), Coordinate2D(2, 2)),
            Pair(GrandWhitePawn(player1), Coordinate2D(3, 2)),
            Pair(GrandWhitePawn(player1), Coordinate2D(4, 2)),
            Pair(GrandWhitePawn(player1), Coordinate2D(5, 2)),
            Pair(GrandWhitePawn(player1), Coordinate2D(6, 2)),
            Pair(GrandWhitePawn(player1), Coordinate2D(7, 2)),
            Pair(GrandWhitePawn(player1), Coordinate2D(8, 2)),
            Pair(GrandWhitePawn(player1), Coordinate2D(9, 2)),

            Pair(GrandBlackPawn(player2), Coordinate2D(0, 7)),
            Pair(GrandBlackPawn(player2), Coordinate2D(1, 7)),
            Pair(GrandBlackPawn(player2), Coordinate2D(2, 7)),
            Pair(GrandBlackPawn(player2), Coordinate2D(3, 7)),
            Pair(GrandBlackPawn(player2), Coordinate2D(4, 7)),
            Pair(GrandBlackPawn(player2), Coordinate2D(5, 7)),
            Pair(GrandBlackPawn(player2), Coordinate2D(6, 7)),
            Pair(GrandBlackPawn(player2), Coordinate2D(7, 7)),
            Pair(GrandBlackPawn(player2), Coordinate2D(8, 7)),
            Pair(GrandBlackPawn(player2), Coordinate2D(9, 7)),
            // Rooks
            Pair(Rook(player1), Coordinate2D(0, 0)),
            Pair(Rook(player1), Coordinate2D(9, 0)),
            Pair(Rook(player2), Coordinate2D(0, 9)),
            Pair(Rook(player2), Coordinate2D(9, 9)),
            // Knights
            Pair(Knight(player1), Coordinate2D(1, 1)),
            Pair(Knight(player1), Coordinate2D(8, 1)),
            Pair(Knight(player2), Coordinate2D(1, 8)),
            Pair(Knight(player2), Coordinate2D(8, 8)),
            // Bishops
            Pair(Bishop(player1), Coordinate2D(2, 1)),
            Pair(Bishop(player1), Coordinate2D(7, 1)),
            Pair(Bishop(player2), Coordinate2D(2, 8)),
            Pair(Bishop(player2), Coordinate2D(7, 8)),
            // Cardinals
            Pair(Cardinal(player1), Coordinate2D(6, 1)),
            Pair(Cardinal(player2), Coordinate2D(6, 8)),
            // Marshals
            Pair(Marshal(player1), Coordinate2D(5, 1)),
            Pair(Marshal(player2), Coordinate2D(5, 8)),
            // Queens
            Pair(Queen(player1), Coordinate2D(3, 1)),
            Pair(Queen(player2), Coordinate2D(3, 8)),
            // Kings
            Pair(King(player1), Coordinate2D(4, 1)),
            Pair(King(player2), Coordinate2D(4, 8))
        )

        mockGrandChess.initGame()
        val initPieces = board.getPieces()
        Assertions.assertTrue(initPieces.containsAll(initPiecesTest))
        Assertions.assertEquals(initPieces.size, initPiecesTest.size)
    }

    @Test
    fun testGrandChessInitialPositionsWithDepth1() {
        perft.testSimple(GrandChess(), 1, 65)
    }

    @Test
    fun testGrandChessInitialPositionsWithDepth2() {
        perft.testSimple(GrandChess(), 2, 4225)
    }

//    @Test
//    fun testGrandChessInitialPositionsWithDepth3() {
//        perft.testSimple(GrandChess(), 3, 259514)
//    }
//
//    @Test
//    fun testGrandChessInitialPositionsWithDepth4() {
//        perft.testSimple(GrandChess(), 4, 15921643)
//    }
}
