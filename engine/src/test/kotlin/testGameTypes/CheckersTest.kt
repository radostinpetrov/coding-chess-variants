package testGameTypes

import coordinates.Coordinate2D
import gameTypes.checkers.Checkers
import io.mockk.MockKAnnotations
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.*
import testPerft.PerftUtility.testSimple

class CheckersTest {
    val mockCheckers = Checkers()

    private val board = mockCheckers.board

    val player1 = mockCheckers.players[0]
    val player2 = mockCheckers.players[1]

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
        testSimple(Checkers(), 1, 7)
    }

    @Test
    fun testCheckersInitialPositionsWithDepth2() {
        testSimple(Checkers(), 2, 49)
    }

    @Test
    fun testCheckersInitialPositionsWithDepth3() {
        testSimple(Checkers(), 3, 302)
    }

    @Test
    fun testCheckersInitialPositionsWithDepth4() {
        testSimple(Checkers(), 4, 1469)
    }

//
//    @Test
//    fun testCheckersInitialPositionsWithDepth5() {
//        testSimple(Checkers(), 5, 7361)
//    }
//
//    @Test
//    fun testCheckersInitialPositionsWithDepth6() {
//        testSimple(Checkers(), 6, 36768)
//    }
}
