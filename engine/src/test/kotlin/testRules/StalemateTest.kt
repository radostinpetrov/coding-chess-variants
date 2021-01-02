package testRules

import coordinates.Coordinate2D
import gameMoves.GameMove2D
import gameMoves.GameMove2D.SimpleGameMove.BasicGameMove
import gameTypes.chess.StandardChess
import gameTypes.xiangqi.Janggi
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.*
import pieces.janggi.General

class StalemateTest {
    val mockStandardChess = spyk<StandardChess>()
    val mockJanggiChess = spyk<Janggi>()
    val board = mockStandardChess.board

    val player1 = mockStandardChess.players[0]
    val player2 = mockStandardChess.players[1]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun quickStalemateTest() {
        mockStandardChess.initGame()

        val turn10Stalemate: List<BasicGameMove> = listOf(
            BasicGameMove(Coordinate2D(4, 1), Coordinate2D(4, 2), StandardWhitePawn(player1), player1),
            BasicGameMove(Coordinate2D(0, 6), Coordinate2D(0, 4), StandardBlackPawn(player2), player2),
            BasicGameMove(Coordinate2D(3, 0), Coordinate2D(7, 4), Queen(player1), player1),
            BasicGameMove(Coordinate2D(0, 7), Coordinate2D(0, 5), Rook(player2), player2),
            BasicGameMove(Coordinate2D(7, 4), Coordinate2D(0, 4), Queen(player1), player1),
            BasicGameMove(Coordinate2D(7, 6), Coordinate2D(7, 4), StandardBlackPawn(player2), player2),
            BasicGameMove(
                Coordinate2D(0, 4), Coordinate2D(2, 6), Queen(player1), player1, board.getPiece(
                    Coordinate2D(2, 6)
                )),
            BasicGameMove(
                Coordinate2D(0, 5), Coordinate2D(7, 5), Rook(player2), player2, board.getPiece(
                    Coordinate2D(7, 5)
                )),
            BasicGameMove(Coordinate2D(7, 1), Coordinate2D(7, 3), StandardWhitePawn(player1), player1),
            BasicGameMove(Coordinate2D(5, 6), Coordinate2D(5, 5), StandardBlackPawn(player2), player2),
            BasicGameMove(Coordinate2D(2, 6), Coordinate2D(3, 6), Queen(player1), player1),
            BasicGameMove(Coordinate2D(4, 7), Coordinate2D(5, 6), King(player2), player2),
            BasicGameMove(
                Coordinate2D(3, 6), Coordinate2D(1, 6), Queen(player1), player1, board.getPiece(
                    Coordinate2D(1, 6)
                )),
            BasicGameMove(Coordinate2D(3, 7), Coordinate2D(3, 2), Queen(player2), player2),
            BasicGameMove(
                Coordinate2D(1, 6), Coordinate2D(1, 7), Queen(player1), player1, board.getPiece(
                    Coordinate2D(1, 7)
                )),
            BasicGameMove(Coordinate2D(3, 2), Coordinate2D(7, 6), Queen(player2), player2),
            BasicGameMove(
                Coordinate2D(1, 7), Coordinate2D(2, 7), Queen(player1), player1, board.getPiece(
                    Coordinate2D(2, 7)
                )),
            BasicGameMove(Coordinate2D(5, 6), Coordinate2D(6, 5), King(player2), player2),
            BasicGameMove(Coordinate2D(2, 7), Coordinate2D(4, 5), Queen(player1), player1),
        )

        for (move in turn10Stalemate) {
            mockStandardChess.makeMove(move)
        }

        val outcome = mockStandardChess.getOutcome(player2)
        assertEquals(Outcome.Draw("Stalemate by No Legal Moves"), outcome)
    }

    @Test
    fun threeFoldRepetitionStalemateTest() {
        mockJanggiChess.initGame()

        val general1Pos = Coordinate2D(4, 1)
        val general1 = mockJanggiChess.board.getPiece(general1Pos)
        val general2Pos = Coordinate2D(4, 8)
        val general2 = mockJanggiChess.board.getPiece(general2Pos)

        val stalemateMoves: List<BasicGameMove> = listOf(
            BasicGameMove(general1Pos, general1Pos, general1!!, player1),
            BasicGameMove(general2Pos, general2Pos, general2!!, player2),
            BasicGameMove(general1Pos, general1Pos, general1, player1),
            )

        for (move in stalemateMoves) {
            mockJanggiChess.makeMove(move)
        }

        val outcome = mockJanggiChess.getOutcome(player2)
        assertEquals(Outcome.Draw("Stalemate by Threefold Repetition"), outcome)
    }
}
