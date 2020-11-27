package testGameTypes

import Coordinate
import gameTypes.xiangqi.Xiangqi
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.xiangqi.*
import players.Player

class XiangqiTest {

    private var mockXiangqi = spyk<Xiangqi>()

    private val board = mockXiangqi.board

    val mockHumanPlayer1 = mockk<Player>()
    val mockHumanPlayer2 = mockk<Player>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun boardIsInitialized() {

        val initPiecesTest = listOf(
            // Soldiers
            Pair(XiangqiRedSoldier(mockHumanPlayer1), Coordinate(0, 3)),
            Pair(XiangqiRedSoldier(mockHumanPlayer1), Coordinate(2, 3)),
            Pair(XiangqiRedSoldier(mockHumanPlayer1), Coordinate(4, 3)),
            Pair(XiangqiRedSoldier(mockHumanPlayer1), Coordinate(6, 3)),
            Pair(XiangqiRedSoldier(mockHumanPlayer1), Coordinate(8, 3)),

            Pair(XiangqiBlueSoldier(mockHumanPlayer2), Coordinate(0, 6)),
            Pair(XiangqiBlueSoldier(mockHumanPlayer2), Coordinate(2, 6)),
            Pair(XiangqiBlueSoldier(mockHumanPlayer2), Coordinate(4, 6)),
            Pair(XiangqiBlueSoldier(mockHumanPlayer2), Coordinate(6, 6)),
            Pair(XiangqiBlueSoldier(mockHumanPlayer2), Coordinate(8, 6)),
            // Chariots
            Pair(XiangqiChariot(mockHumanPlayer1), Coordinate(0, 0)),
            Pair(XiangqiChariot(mockHumanPlayer1), Coordinate(8, 0)),
            Pair(XiangqiChariot(mockHumanPlayer2), Coordinate(0, 9)),
            Pair(XiangqiChariot(mockHumanPlayer2), Coordinate(8, 9)),
            // Cannons
            Pair(XiangqiCannon(mockHumanPlayer1), Coordinate(1, 2)),
            Pair(XiangqiCannon(mockHumanPlayer1), Coordinate(7, 2)),
            Pair(XiangqiCannon(mockHumanPlayer2), Coordinate(1, 7)),
            Pair(XiangqiCannon(mockHumanPlayer2), Coordinate(7, 7)),
            // Horses
            Pair(XiangqiHorse(mockHumanPlayer1), Coordinate(1, 0)),
            Pair(XiangqiHorse(mockHumanPlayer1), Coordinate(7, 0)),
            Pair(XiangqiHorse(mockHumanPlayer2), Coordinate(1, 9)),
            Pair(XiangqiHorse(mockHumanPlayer2), Coordinate(7, 9)),
            // Elephants
            Pair(XiangqiRedElephant(mockHumanPlayer1), Coordinate(2, 0)),
            Pair(XiangqiRedElephant(mockHumanPlayer1), Coordinate(6, 0)),
            Pair(XiangqiBlueElephant(mockHumanPlayer2), Coordinate(2, 9)),
            Pair(XiangqiBlueElephant(mockHumanPlayer2), Coordinate(6, 9)),
            // Advisors
            Pair(XiangqiAdvisor(mockHumanPlayer1), Coordinate(3, 0)),
            Pair(XiangqiAdvisor(mockHumanPlayer1), Coordinate(5, 0)),
            Pair(XiangqiAdvisor(mockHumanPlayer2), Coordinate(3, 9)),
            Pair(XiangqiAdvisor(mockHumanPlayer2), Coordinate(5, 9)),
            // Generals
            Pair(XiangqiGeneral(mockHumanPlayer1), Coordinate(4, 0)),
            Pair(XiangqiGeneral(mockHumanPlayer2), Coordinate(4, 9))
        )
        mockXiangqi.addPlayer(mockHumanPlayer1)
        mockXiangqi.addPlayer(mockHumanPlayer2)
        mockXiangqi.initGame()
        val initPieces = board.getPieces()
        Assertions.assertTrue(initPieces.containsAll(initPiecesTest))
        Assertions.assertEquals(initPieces.size, initPiecesTest.size)
    }
}
