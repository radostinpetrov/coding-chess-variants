package testGameTypes

import Coordinate
import gameTypes.xiangqi.Janggi
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.janggi.*
import players.Player

class JanggiTest {
    private var mockJanggi = spyk<Janggi>()

    private val board = mockJanggi.board

    val mockHumanPlayer1 = mockk<Player>()
    val mockHumanPlayer2 = mockk<Player>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun boardIsInitialized() {

        val initPiecesTest = listOf(
            // Soldiers
            Pair(RedSoldier(mockHumanPlayer1), Coordinate(0, 3)),
            Pair(RedSoldier(mockHumanPlayer1), Coordinate(2, 3)),
            Pair(RedSoldier(mockHumanPlayer1), Coordinate(4, 3)),
            Pair(RedSoldier(mockHumanPlayer1), Coordinate(6, 3)),
            Pair(RedSoldier(mockHumanPlayer1), Coordinate(8, 3)),

            Pair(BlueSoldier(mockHumanPlayer2), Coordinate(0, 6)),
            Pair(BlueSoldier(mockHumanPlayer2), Coordinate(2, 6)),
            Pair(BlueSoldier(mockHumanPlayer2), Coordinate(4, 6)),
            Pair(BlueSoldier(mockHumanPlayer2), Coordinate(6, 6)),
            Pair(BlueSoldier(mockHumanPlayer2), Coordinate(8, 6)),
            // Chariots
            Pair(Chariot(mockHumanPlayer1), Coordinate(0, 0)),
            Pair(Chariot(mockHumanPlayer1), Coordinate(8, 0)),
            Pair(Chariot(mockHumanPlayer2), Coordinate(0, 9)),
            Pair(Chariot(mockHumanPlayer2), Coordinate(8, 9)),
            // Cannons
            Pair(Cannon(mockHumanPlayer1), Coordinate(1, 2)),
            Pair(Cannon(mockHumanPlayer1), Coordinate(7, 2)),
            Pair(Cannon(mockHumanPlayer2), Coordinate(1, 7)),
            Pair(Cannon(mockHumanPlayer2), Coordinate(7, 7)),
            // Horses
            Pair(Horse(mockHumanPlayer1), Coordinate(1, 0)),
            Pair(Horse(mockHumanPlayer1), Coordinate(7, 0)),
            Pair(Horse(mockHumanPlayer2), Coordinate(1, 9)),
            Pair(Horse(mockHumanPlayer2), Coordinate(7, 9)),
            // Elephants
            Pair(Elephant(mockHumanPlayer1), Coordinate(2, 0)),
            Pair(Elephant(mockHumanPlayer1), Coordinate(6, 0)),
            Pair(Elephant(mockHumanPlayer2), Coordinate(2, 9)),
            Pair(Elephant(mockHumanPlayer2), Coordinate(6, 9)),
            // Advisors
            Pair(Advisor(mockHumanPlayer1), Coordinate(3, 0)),
            Pair(Advisor(mockHumanPlayer1), Coordinate(5, 0)),
            Pair(Advisor(mockHumanPlayer2), Coordinate(3, 9)),
            Pair(Advisor(mockHumanPlayer2), Coordinate(5, 9)),
            // Generals
            Pair(General(mockHumanPlayer1), Coordinate(4, 1)),
            Pair(General(mockHumanPlayer2), Coordinate(4, 8))
        )
        mockJanggi.addPlayer(mockHumanPlayer1)
        mockJanggi.addPlayer(mockHumanPlayer2)
        mockJanggi.initGame()
        val initPieces = board.getPieces()
        Assertions.assertTrue(initPieces.containsAll(initPiecesTest))
        Assertions.assertEquals(initPieces.size, initPiecesTest.size)
    }
}
