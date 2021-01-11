package testGameTypes

import boards.Board2D
import coordinates.Coordinate2D
import gameTypes.GameType2D
import gameTypes.xiangqi.Xiangqi
import io.mockk.MockKAnnotations
import io.mockk.spyk
import moveGenerators.MoveGenerator2D
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.Piece2D
import pieces.xiangqi.*
import testPerft.PerftUtility

class XiangqiTest {
    private var mockXiangqi = spyk<Xiangqi>()

    private val board = mockXiangqi.board

    val player1 = mockXiangqi.players[0]
    val player2 = mockXiangqi.players[1]

    val perft = PerftUtility<GameType2D, Board2D, MoveGenerator2D, Piece2D, Coordinate2D>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun boardIsInitialized() {
        val initPiecesTest = listOf(
            // Soldiers
            Pair(XiangqiRedSoldier(player1), Coordinate2D(0, 3)),
            Pair(XiangqiRedSoldier(player1), Coordinate2D(2, 3)),
            Pair(XiangqiRedSoldier(player1), Coordinate2D(4, 3)),
            Pair(XiangqiRedSoldier(player1), Coordinate2D(6, 3)),
            Pair(XiangqiRedSoldier(player1), Coordinate2D(8, 3)),

            Pair(XiangqiBlueSoldier(player2), Coordinate2D(0, 6)),
            Pair(XiangqiBlueSoldier(player2), Coordinate2D(2, 6)),
            Pair(XiangqiBlueSoldier(player2), Coordinate2D(4, 6)),
            Pair(XiangqiBlueSoldier(player2), Coordinate2D(6, 6)),
            Pair(XiangqiBlueSoldier(player2), Coordinate2D(8, 6)),
            // Chariots
            Pair(XiangqiChariot(player1), Coordinate2D(0, 0)),
            Pair(XiangqiChariot(player1), Coordinate2D(8, 0)),
            Pair(XiangqiChariot(player2), Coordinate2D(0, 9)),
            Pair(XiangqiChariot(player2), Coordinate2D(8, 9)),
            // Cannons
            Pair(XiangqiCannon(player1), Coordinate2D(1, 2)),
            Pair(XiangqiCannon(player1), Coordinate2D(7, 2)),
            Pair(XiangqiCannon(player2), Coordinate2D(1, 7)),
            Pair(XiangqiCannon(player2), Coordinate2D(7, 7)),
            // Horses
            Pair(XiangqiHorse(player1), Coordinate2D(1, 0)),
            Pair(XiangqiHorse(player1), Coordinate2D(7, 0)),
            Pair(XiangqiHorse(player2), Coordinate2D(1, 9)),
            Pair(XiangqiHorse(player2), Coordinate2D(7, 9)),
            // Elephants
            Pair(XiangqiRedElephant(player1), Coordinate2D(2, 0)),
            Pair(XiangqiRedElephant(player1), Coordinate2D(6, 0)),
            Pair(XiangqiBlueElephant(player2), Coordinate2D(2, 9)),
            Pair(XiangqiBlueElephant(player2), Coordinate2D(6, 9)),
            // Advisors
            Pair(XiangqiAdvisor(player1), Coordinate2D(3, 0)),
            Pair(XiangqiAdvisor(player1), Coordinate2D(5, 0)),
            Pair(XiangqiAdvisor(player2), Coordinate2D(3, 9)),
            Pair(XiangqiAdvisor(player2), Coordinate2D(5, 9)),
            // Generals
            Pair(XiangqiGeneral(player1), Coordinate2D(4, 0)),
            Pair(XiangqiGeneral(player2), Coordinate2D(4, 9))
        )

        mockXiangqi.initGame()
        val initPieces = board.getPieces()
        Assertions.assertTrue(initPieces.containsAll(initPiecesTest))
        Assertions.assertEquals(initPieces.size, initPiecesTest.size)
    }

    @Test
    fun testXiangqiInitialPositionsWithDepth1() {
        perft.testSimple(Xiangqi(), 1, 44)
    }

    @Test
    fun testXiangqiInitialPositionsWithDepth2() {
        perft.testSimple(Xiangqi(), 2, 1920)
    }

//    @Test
//    fun testXiangqiInitialPositionsWithDepth3() {
//        perft.testSimple(Xiangqi(), 3, 79666)
//    }
//
//    @Test
//    fun testXiangqiInitialPositionsWithDepth4() {
//        perft.testSimple(Xiangqi(), 4, 3920240)
//    }
}
