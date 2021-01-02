package testRules

import coordinates.Coordinate2D
import gameMoves.GameMove2D
import gameTypes.chess.StandardChess
import gameTypes.chess.winconditions.FiftyMoveRuleStalemate
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

class FiftyMoveRuleStalemateTest {
    val mockStandardChess = spyk<StandardChess>()

    val player1 = mockStandardChess.players[0]
    val player2 = mockStandardChess.players[1]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    private fun setUpTestMoveList(): MutableList<GameMove2D> {
        val moveList = mutableListOf<GameMove2D>()
        for (i in 0..100) {
            moveList.add( GameMove2D.SimpleGameMove.BasicGameMove(Coordinate2D(0, 0), Coordinate2D(0, 0), King(player1), player1))
        }

        return moveList
    }

    private fun fiftyMoveStalemateTestHelper(testMove: GameMove2D) {
        val moveList = setUpTestMoveList()
        moveList.add(testMove)
        val outcome = FiftyMoveRuleStalemate().evaluate(mockStandardChess, player1, moveList)
        Assertions.assertNull(outcome)
    }

    @Test
    fun noFiftyMoveStalemateStandardChessPawnTest() {
        fiftyMoveStalemateTestHelper(
            GameMove2D.SimpleGameMove.BasicGameMove(Coordinate2D(0, 0), Coordinate2D(0, 0), StandardWhitePawn(player1), player1)
        )
        fiftyMoveStalemateTestHelper(
            GameMove2D.SimpleGameMove.BasicGameMove(Coordinate2D(0, 0), Coordinate2D(0, 0), StandardBlackPawn(player1), player1)
        )
    }

    @Test
    fun noFiftyMoveStalemateAntiChessPawnTest() {
        fiftyMoveStalemateTestHelper(
            GameMove2D.SimpleGameMove.BasicGameMove(Coordinate2D(0, 0), Coordinate2D(0, 0), AntiChessWhitePawn(player1), player1)
        )
        fiftyMoveStalemateTestHelper(
            GameMove2D.SimpleGameMove.BasicGameMove(Coordinate2D(0, 0), Coordinate2D(0, 0), AntiChessBlackPawn(player1), player1)
        )
    }

    @Test
    fun noFiftyMoveStalemateCapblancaChessPawnTest() {
        fiftyMoveStalemateTestHelper(
            GameMove2D.SimpleGameMove.BasicGameMove(Coordinate2D(0, 0), Coordinate2D(0, 0), CapablancaWhitePawn(player1), player1)
        )
        fiftyMoveStalemateTestHelper(
            GameMove2D.SimpleGameMove.BasicGameMove(Coordinate2D(0, 0), Coordinate2D(0, 0), CapablancaBlackPawn(player1), player1)
        )
    }

    @Test
    fun noFiftyMoveStalemateGrandChessPawnTest() {
        fiftyMoveStalemateTestHelper(
            GameMove2D.SimpleGameMove.BasicGameMove(Coordinate2D(0, 0), Coordinate2D(0, 0), GrandWhitePawn(player1), player1)
        )
        fiftyMoveStalemateTestHelper(
            GameMove2D.SimpleGameMove.BasicGameMove(Coordinate2D(0, 0), Coordinate2D(0, 0), GrandBlackPawn(player1), player1)
        )
    }

    @Test
    fun noFiftyMoveStalemateXiangqiSoldierTest() {
        fiftyMoveStalemateTestHelper(
            GameMove2D.SimpleGameMove.BasicGameMove(Coordinate2D(0, 0), Coordinate2D(0, 0), XiangqiBlueSoldier(player1), player1)
        )
        fiftyMoveStalemateTestHelper(
            GameMove2D.SimpleGameMove.BasicGameMove(Coordinate2D(0, 0), Coordinate2D(0, 0), XiangqiRedSoldier(player1), player1)
        )
    }

    @Test
    fun noFiftyMoveStalemateJanggiSoldierTest() {
        fiftyMoveStalemateTestHelper(
            GameMove2D.SimpleGameMove.BasicGameMove(Coordinate2D(0, 0), Coordinate2D(0, 0), BlueSoldier(player1), player1)
        )
        fiftyMoveStalemateTestHelper(
            GameMove2D.SimpleGameMove.BasicGameMove(Coordinate2D(0, 0), Coordinate2D(0, 0), RedSoldier(player1), player1)
        )
    }

    @Test
    fun noFiftyMoveStalemateCaptureTest() {
        fiftyMoveStalemateTestHelper(
            GameMove2D.SimpleGameMove.BasicGameMove(Coordinate2D(0, 0), Coordinate2D(0, 0), BlueSoldier(player1), player1, pieceCaptured = RedSoldier(player2))
        )
    }

    @Test
    fun fiftyMoveStalemateTest() {
        val moveList = setUpTestMoveList()
        val outcome = FiftyMoveRuleStalemate().evaluate(mockStandardChess, player1, moveList)
        Assertions.assertEquals(Outcome.Draw("by 50-move rule"), outcome)
    }
}