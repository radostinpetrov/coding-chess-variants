package testPerft

import gameTypes.chess.AbstractChess
import org.junit.jupiter.api.Assertions

object PerftUtility {
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

    fun perft(depth: Int, game: AbstractChess): PerftData {
        if (depth == 0) {
            return PerftData(1, 0, 0, 0)
        }

        val moves = game.getValidMoves(game.getCurrentPlayer())

        var data = PerftData(0, 0, 0, 0)

        for (move in moves) {
            game.playerMakeMove(move)
//            game.getValidMoves(game.getCurrentPlayer())

            if (depth == 1) {
                if (move.displayPieceCaptured != null) {
                    data.captures += 1
                }

                if (game.inCheck(game.getCurrentPlayer())) {
                    data.checks += 1
                }

                if (game.checkmate) {
                    data.checkmates += 1
                }
            }

            data.plus(perft(depth - 1, game))

            game.undoMove()
            game.nextPlayer()
        }

        return data
    }

    fun test(game: AbstractChess, depth: Int, expectedData: PerftData) {
        game.initGame()
        Assertions.assertEquals(expectedData, perft(depth, game))
    }

    fun testSimple(game: AbstractChess, depth: Int, expectedNodes: Int) {
        game.initGame()
        Assertions.assertEquals(expectedNodes, perft(depth, game).nodes)
    }
}