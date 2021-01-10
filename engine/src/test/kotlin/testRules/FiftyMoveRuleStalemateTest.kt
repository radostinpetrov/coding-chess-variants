package testRules

import coordinates.Coordinate2D
import moves.*
import gameTypes.chess.StandardChess
import endconditions.FiftyMoveRuleStalemate
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.antichess.AntiChessBlackPawn
import pieces.antichess.AntiChessWhitePawn
import pieces.chess.*
import pieces.janggi.BlueSoldier
import pieces.janggi.RedSoldier
import pieces.xiangqi.XiangqiBlueSoldier
import pieces.xiangqi.XiangqiRedSoldier
import endconditions.Outcome
import moves.BasicMove2D

class FiftyMoveRuleStalemateTest {
    val mockStandardChess = spyk<StandardChess>()

    val player1 = mockStandardChess.players[0]
    val player2 = mockStandardChess.players[1]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    private fun setUpTestMoveList(): MutableList<Move2D> {
        val moveList = mutableListOf<Move2D>()
        for (i in 0..100) {
            moveList.add( BasicMove2D(Coordinate2D(0, 0), Coordinate2D(0, 0), King(player1), player1))
        }

        return moveList
    }

    private fun fiftyMoveStalemateTestHelper(testMove: Move2D) {
        val moveList = setUpTestMoveList()
        moveList.add(testMove)
        val outcome = FiftyMoveRuleStalemate().evaluate(mockStandardChess, player1, moveList)
        Assertions.assertNull(outcome)
    }

    @Test
    fun noFiftyMoveStalemateStandardChessPawnTest() {
        fiftyMoveStalemateTestHelper(
            BasicMove2D(Coordinate2D(0, 0), Coordinate2D(0, 0), StandardWhitePawn(player1), player1)
        )
        fiftyMoveStalemateTestHelper(
            BasicMove2D(Coordinate2D(0, 0), Coordinate2D(0, 0), StandardBlackPawn(player1), player1)
        )
    }

    @Test
    fun noFiftyMoveStalemateAntiChessPawnTest() {
        fiftyMoveStalemateTestHelper(
            BasicMove2D(Coordinate2D(0, 0), Coordinate2D(0, 0), AntiChessWhitePawn(player1), player1)
        )
        fiftyMoveStalemateTestHelper(
            BasicMove2D(Coordinate2D(0, 0), Coordinate2D(0, 0), AntiChessBlackPawn(player1), player1)
        )
    }

    @Test
    fun noFiftyMoveStalemateCapblancaChessPawnTest() {
        fiftyMoveStalemateTestHelper(
            BasicMove2D(Coordinate2D(0, 0), Coordinate2D(0, 0), CapablancaWhitePawn(player1), player1)
        )
        fiftyMoveStalemateTestHelper(
            BasicMove2D(Coordinate2D(0, 0), Coordinate2D(0, 0), CapablancaBlackPawn(player1), player1)
        )
    }

    @Test
    fun noFiftyMoveStalemateGrandChessPawnTest() {
        fiftyMoveStalemateTestHelper(
            BasicMove2D(Coordinate2D(0, 0), Coordinate2D(0, 0), GrandWhitePawn(player1), player1)
        )
        fiftyMoveStalemateTestHelper(
            BasicMove2D(Coordinate2D(0, 0), Coordinate2D(0, 0), GrandBlackPawn(player1), player1)
        )
    }

    @Test
    fun noFiftyMoveStalemateXiangqiSoldierTest() {
        fiftyMoveStalemateTestHelper(
            BasicMove2D(Coordinate2D(0, 0), Coordinate2D(0, 0), XiangqiBlueSoldier(player1), player1)
        )
        fiftyMoveStalemateTestHelper(
            BasicMove2D(Coordinate2D(0, 0), Coordinate2D(0, 0), XiangqiRedSoldier(player1), player1)
        )
    }

    @Test
    fun noFiftyMoveStalemateJanggiSoldierTest() {
        fiftyMoveStalemateTestHelper(
            BasicMove2D(Coordinate2D(0, 0), Coordinate2D(0, 0), BlueSoldier(player1), player1)
        )
        fiftyMoveStalemateTestHelper(
            BasicMove2D(Coordinate2D(0, 0), Coordinate2D(0, 0), RedSoldier(player1), player1)
        )
    }

    @Test
    fun noFiftyMoveStalemateCaptureTest() {
        fiftyMoveStalemateTestHelper(
            BasicMove2D(Coordinate2D(0, 0), Coordinate2D(0, 0), BlueSoldier(player1), player1, pieceCaptured = RedSoldier(player2))
        )
    }

    @Test
    fun fiftyMoveStalemateTest() {
        val moveList = setUpTestMoveList()
        val outcome = FiftyMoveRuleStalemate().evaluate(mockStandardChess, player1, moveList)
        Assertions.assertEquals(Outcome.Draw("by 50-move rule"), outcome)
    }

    @Test
    fun noFiftyMoveStalemateTest() {
        val moveList = listOf<Move2D>()
        val outcome = FiftyMoveRuleStalemate().evaluate(mockStandardChess, player1, moveList)
        Assertions.assertNull(outcome)
    }
}