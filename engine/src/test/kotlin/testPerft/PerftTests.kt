package testPerft

import gameTypes.GameType
import gameTypes.chess.StandardChess
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PerftTests {
    private fun perft(depth: Int, gameType: GameType): Int {
        if (depth == 0) {
            return 1
        }

        val moves = gameType.getValidMoves(gameType.getCurrentPlayer())
        var nodes = 0
        for (move in moves) {
            gameType.playerMakeMove(move)
            nodes += perft(depth - 1, gameType)

            gameType.undoMove()
            gameType.nextPlayer()
        }

        return nodes
    }

    private fun testHelper(gameType: GameType, depth: Int, expected: Int) {
        gameType.initGame()
        assertEquals(perft(depth, gameType), expected)
    }

    @Test
    fun testStandardChessInitialPositionsWithDepth1() {
        testHelper(StandardChess(), 1, 20)
    }

    @Test
    fun testStandardChessInitialPositionsWithDepth2() {
        testHelper(StandardChess(), 2, 400)
    }

    @Test
    fun testStandardChessInitialPositionsWithDepth3() {
        testHelper(StandardChess(), 3, 8902)
    }

    @Test
    fun testStandardChessInitialPositionsWithDepth4() {
        testHelper(StandardChess(), 4, 197281)
    }
}