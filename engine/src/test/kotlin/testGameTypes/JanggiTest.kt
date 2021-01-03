package testGameTypes

import coordinates.Coordinate2D
import gameTypes.xiangqi.Janggi
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.janggi.*
import testPerft.PerftUtility

class JanggiTest {
    private var mockJanggi = spyk<Janggi>()

    private val board = mockJanggi.board

    val player1 = mockJanggi.players[0]
    val player2 = mockJanggi.players[1]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun boardIsInitialized() {

        val initPiecesTest = listOf(
            // Soldiers
            Pair(RedSoldier(player1), Coordinate2D(0, 3)),
            Pair(RedSoldier(player1), Coordinate2D(2, 3)),
            Pair(RedSoldier(player1), Coordinate2D(4, 3)),
            Pair(RedSoldier(player1), Coordinate2D(6, 3)),
            Pair(RedSoldier(player1), Coordinate2D(8, 3)),

            Pair(BlueSoldier(player2), Coordinate2D(0, 6)),
            Pair(BlueSoldier(player2), Coordinate2D(2, 6)),
            Pair(BlueSoldier(player2), Coordinate2D(4, 6)),
            Pair(BlueSoldier(player2), Coordinate2D(6, 6)),
            Pair(BlueSoldier(player2), Coordinate2D(8, 6)),
            // Chariots
            Pair(Chariot(player1), Coordinate2D(0, 0)),
            Pair(Chariot(player1), Coordinate2D(8, 0)),
            Pair(Chariot(player2), Coordinate2D(0, 9)),
            Pair(Chariot(player2), Coordinate2D(8, 9)),
            // Cannons
            Pair(Cannon(player1), Coordinate2D(1, 2)),
            Pair(Cannon(player1), Coordinate2D(7, 2)),
            Pair(Cannon(player2), Coordinate2D(1, 7)),
            Pair(Cannon(player2), Coordinate2D(7, 7)),
            // Horses
            Pair(Horse(player1), Coordinate2D(1, 0)),
            Pair(Horse(player1), Coordinate2D(7, 0)),
            Pair(Horse(player2), Coordinate2D(1, 9)),
            Pair(Horse(player2), Coordinate2D(7, 9)),
            // Elephants
            Pair(Elephant(player1), Coordinate2D(2, 0)),
            Pair(Elephant(player1), Coordinate2D(6, 0)),
            Pair(Elephant(player2), Coordinate2D(2, 9)),
            Pair(Elephant(player2), Coordinate2D(6, 9)),
            // Advisors
            Pair(Advisor(player1), Coordinate2D(3, 0)),
            Pair(Advisor(player1), Coordinate2D(5, 0)),
            Pair(Advisor(player2), Coordinate2D(3, 9)),
            Pair(Advisor(player2), Coordinate2D(5, 9)),
            // Generals
            Pair(General(player1), Coordinate2D(4, 1)),
            Pair(General(player2), Coordinate2D(4, 8))
        )

        mockJanggi.initGame()
        val initPieces = board.getPieces()
        Assertions.assertTrue(initPieces.containsAll(initPiecesTest))
        Assertions.assertEquals(initPieces.size, initPiecesTest.size)
    }

    @Test
    fun testJanggiInitialPositionsWithDepth1() {
        PerftUtility.testSimple(Janggi(), 1, 32)
    }

    @Test
    fun testJanggiInitialPositionsWithDepth2() {
        PerftUtility.testSimple(Janggi(), 2, 1012)
    }

//    @Test
//    fun testJanggiInitialPositionsWithDepth3() {
//        PerftUtility.testSimple(Janggi(), 3, 29697)
//    }
//
//    @Test
//    fun testJanggiInitialPositionsWithDepth4() {
//        PerftUtility.testSimple(Janggi(), 4, 15921643)
//    }
}
