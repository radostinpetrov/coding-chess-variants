package testPerft

import gameTypes.GameType
import gameTypes.chess.StandardChess
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PerftTests {

    class PerftData(var nodes: Int, var captures: Int, var checks: Int) {
        fun plus(other: PerftData) {
            nodes += other.nodes
            captures += other.captures
            checks += other.checks
        }

        override fun equals(other: Any?): Boolean {
            val otherData = other as PerftData

            return ((otherData.nodes == nodes) and (otherData.captures == captures) && otherData.checks == checks)
        }

        override fun toString(): String {
            return ("PerftData(nodes = $nodes, captures = $captures, checks = $checks)")
        }
    }

    private fun perft(depth: Int, gameType: GameType): PerftData {
        if (depth == 0) {
            return PerftData(1, 0, 0)
        }

        val moves = gameType.getValidMoves(gameType.getCurrentPlayer())
        var data = PerftData(0, 0, 0)

        for (move in moves) {
            gameType.playerMakeMove(move)

            if (move.displayPieceCaptured != null) {
                data.captures += 1
            }

            if (gameType.inCheck(gameType.getCurrentPlayer())) {
                data.checks += 1
            }

            data.plus(perft(depth - 1, gameType))

            gameType.undoMove()
            gameType.nextPlayer()
        }

        return data
    }

    private fun testHelper(gameType: GameType, depth: Int, expectedData: PerftData) {
        gameType.initGame()
        assertEquals(perft(depth, gameType), expectedData)
    }

    @Test
    fun testStandardChessInitialPositionsWithDepth1() {
        testHelper(StandardChess(), 1, PerftData(20 ,0, 0))
    }

    @Test
    fun testStandardChessInitialPositionsWithDepth2() {
        testHelper(StandardChess(), 2, PerftData(400 ,0, 0))
    }

    @Test
    fun testStandardChessInitialPositionsWithDepth3() {
        testHelper(StandardChess(), 3, PerftData(8902 ,34, 12))
    }

    @Test
    fun testStandardChessInitialPositionsWithDepth4() {
        testHelper(StandardChess(), 4, PerftData(197281,1576, 469))
    }
}