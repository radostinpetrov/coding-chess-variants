package testGameTypes

import boards.Board2D
import coordinates.Coordinate2D
import gameTypes.GameType2D
import gameTypes.checkers.Checkers
import io.mockk.MockKAnnotations
import moveGenerators.MoveGenerator2D
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.Piece2D
import testPerft.PerftUtility

class CheckersTest {
    val mockCheckers = Checkers()

    private val board = mockCheckers.board

    val player1 = mockCheckers.players[0]
    val player2 = mockCheckers.players[1]

    val perft = PerftUtility<GameType2D, Board2D, MoveGenerator2D, Piece2D, Coordinate2D>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun boardIsInitialized() {

        val initPiecesTest = listOf(
            // Pawns
            Pair(Checkers.WhiteChecker(player1), Coordinate2D(0, 0)),
            Pair(Checkers.WhiteChecker(player1), Coordinate2D(2, 0)),
            Pair(Checkers.WhiteChecker(player1), Coordinate2D(4, 0)),
            Pair(Checkers.WhiteChecker(player1), Coordinate2D(6, 0)),
            Pair(Checkers.WhiteChecker(player1), Coordinate2D(1, 1)),
            Pair(Checkers.WhiteChecker(player1), Coordinate2D(3, 1)),
            Pair(Checkers.WhiteChecker(player1), Coordinate2D(5, 1)),
            Pair(Checkers.WhiteChecker(player1), Coordinate2D(7, 1)),
            Pair(Checkers.WhiteChecker(player1), Coordinate2D(0, 2)),
            Pair(Checkers.WhiteChecker(player1), Coordinate2D(2, 2)),
            Pair(Checkers.WhiteChecker(player1), Coordinate2D(4, 2)),
            Pair(Checkers.WhiteChecker(player1), Coordinate2D(6, 2)),
            Pair(Checkers.BlackChecker(player2), Coordinate2D(1, 5)),
            Pair(Checkers.BlackChecker(player2), Coordinate2D(3, 5)),
            Pair(Checkers.BlackChecker(player2), Coordinate2D(5, 5)),
            Pair(Checkers.BlackChecker(player2), Coordinate2D(7, 5)),
            Pair(Checkers.BlackChecker(player2), Coordinate2D(0, 6)),
            Pair(Checkers.BlackChecker(player2), Coordinate2D(2, 6)),
            Pair(Checkers.BlackChecker(player2), Coordinate2D(4, 6)),
            Pair(Checkers.BlackChecker(player2), Coordinate2D(6, 6)),
            Pair(Checkers.BlackChecker(player2), Coordinate2D(1, 7)),
            Pair(Checkers.BlackChecker(player2), Coordinate2D(3, 7)),
            Pair(Checkers.BlackChecker(player2), Coordinate2D(5, 7)),
            Pair(Checkers.BlackChecker(player2), Coordinate2D(7, 7))
        )

        mockCheckers.initGame()
        val initPieces = board.getPieces()
        assertTrue(initPieces.containsAll(initPiecesTest))
        Assertions.assertEquals(initPieces.size, initPiecesTest.size)
    }

    @Test
    fun testCheckersInitialPositionsWithDepth1() {
        perft.testSimple(Checkers(), 1, 7)
    }

    @Test
    fun testCheckersInitialPositionsWithDepth2() {
        perft.testSimple(Checkers(), 2, 49)
    }

    @Test
    fun testCheckersInitialPositionsWithDepth3() {
        perft.testSimple(Checkers(), 3, 302)
    }

    @Test
    fun testCheckersInitialPositionsWithDepth4() {
        perft.testSimple(Checkers(), 4, 1469)
    }

//
//    @Test
//    fun testCheckersInitialPositionsWithDepth5() {
//        perft.testSimple(Checkers(), 5, 7361)
//    }
//
//    @Test
//    fun testCheckersInitialPositionsWithDepth6() {
//        perft.testSimple(Checkers(), 6, 36768)
//    }
}
