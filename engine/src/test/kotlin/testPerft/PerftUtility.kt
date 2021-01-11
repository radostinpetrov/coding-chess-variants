package testPerft

import boards.Board
import coordinates.Coordinate
import org.junit.jupiter.api.Assertions
import endconditions.Outcome
import gameTypes.GameType
import moveGenerators.MoveGenerator
import pieces.Piece

class PerftUtility<G : GameType<B, MG, P, C>,
        B : Board<B, MG, P, C>,
        MG : MoveGenerator<B, MG, P, C>,
        P : Piece<B, MG, P, C>,
        C : Coordinate>
{
    data class PerftData(var nodes: Int, var captures: Int, var checks: Int, var checkmates: Int) {
        fun plus(other: PerftData) {
            nodes += other.nodes
            captures += other.captures
            checks += other.checks
            checkmates += other.checkmates
        }
    }

    fun perft(depth: Int, game: G, checkForCheckmate: Boolean = true): PerftData {
        if (depth == 0) {
            return PerftData(1, 0, 0, 0)
        }

        val moves = game.getValidMoves(game.getCurrentPlayer()).distinct()

        val data = PerftData(0, 0, 0, 0)

        for (move in moves) {
            val currPlayer = game.getCurrentPlayer()
            game.playerMakeMove(move)
            if (depth == 1) {
                if (move.displayPieceCaptured != null) {
                    data.captures += 1
                }

                if (game.inCheck(game.getCurrentPlayer())) {
                    data.checks += 1
                }

                if (checkForCheckmate) {
                    val outcome = game.getOutcome()
                    if (outcome != null && outcome is Outcome.Win) {
                        data.checkmates += 1
                    }
                }
            }
            if (currPlayer == game.getCurrentPlayer()) {
                data.plus(perft(depth, game))
                game.undoMove()
            } else {
                data.plus(perft(depth - 1, game))
                game.undoMove()
                game.nextPlayer()
            }
        }

        return data
    }

    fun test(game: G, depth: Int, expectedData: PerftData) {
        game.initGame()
        Assertions.assertEquals(expectedData, perft(depth, game))
    }

    fun testSimple(game: G, depth: Int, expectedNodes: Int) {
        game.initGame()
        Assertions.assertEquals(expectedNodes, perft(depth, game, false).nodes)
    }
}
