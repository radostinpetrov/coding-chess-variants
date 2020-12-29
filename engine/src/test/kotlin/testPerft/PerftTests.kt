package testPerft

import gameTypes.GameType
import gameTypes.chess.AbstractChess
import gameTypes.chess.StandardChess
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PerftTests {

    class PerftData(var nodes: Int, var captures: Int, var checks: Int, var checkmates: Int) {
        fun plus(other: PerftData) {
            nodes += other.nodes
            captures += other.captures
            checks += other.checks
            checkmates += other.checkmates
        }

        override fun equals(other: Any?): Boolean {
            val otherData = other as PerftData

            return ((otherData.nodes == nodes) and (otherData.captures == captures) && otherData.checks == checks)
        }

        override fun toString(): String {
            return ("PerftData(nodes = $nodes, captures = $captures, checks = $checks, checkmates = $checkmates)")
        }
    }

    private fun perft(depth: Int, game: AbstractChess): PerftData {
        if (depth == 0) {
            return PerftData(1, 0, 0, 0)
        }

        val moves = game.getValidMoves(game.getCurrentPlayer()).distinct()
        var data = PerftData(0, 0, 0, 0)

        for (move in moves) {
            game.playerMakeMove(move)
            game.getValidMoves(game.getCurrentPlayer())

            if (move.displayPieceCaptured != null) {
                data.captures += 1
            }

            if (game.inCheck(game.getCurrentPlayer())) {
                data.checks += 1
            }

            if (game.checkmate) {
                data.checkmates += 1
            }

            data.plus(perft(depth - 1, game))

            game.undoMove()
            game.nextPlayer()
        }

        return data
    }

    private fun testHelper(game: AbstractChess, depth: Int, expectedData: PerftData) {
        game.initGame()
        assertEquals(perft(depth, game), expectedData)
    }

    @Test
    fun testStandardChessInitialPositionsWithDepth1() {
        testHelper(StandardChess(), 1, PerftData(20 ,0, 0, 0))
    }

    @Test
    fun testStandardChessInitialPositionsWithDepth2() {
        testHelper(StandardChess(), 2, PerftData(400 ,0, 0, 0))
    }

    @Test
    fun testStandardChessInitialPositionsWithDepth3() {
        testHelper(StandardChess(), 3, PerftData(8902 ,34, 12, 0))
    }

    @Test
    fun testStandardChessInitialPositionsWithDepth4() {
        testHelper(StandardChess(), 4, PerftData(197281,1576, 469, 8))
    }
}