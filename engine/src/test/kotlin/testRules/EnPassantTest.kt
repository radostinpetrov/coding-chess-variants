package testRules

import coordinates.Coordinate2D
import moves.*
import gameTypes.chess.StandardChess
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.StandardBlackPawn
import pieces.chess.StandardWhitePawn

class EnPassantTest {

    val mockStandardChess = spyk<StandardChess>()
    val board = mockStandardChess.board

    val player1 = mockStandardChess.players[0]
    val player2 = mockStandardChess.players[1]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun simplePawnEnPassant() {
        mockStandardChess.initGame()

        val enPassantMove: List<BasicMove2D> = listOf(
            BasicMove2D(
                Coordinate2D(4, 4), Coordinate2D(3, 4), StandardWhitePawn(player1), player1, board.getPiece(
                    Coordinate2D(3, 4)
                )),
            BasicMove2D(Coordinate2D(3, 4), Coordinate2D(3, 5), StandardWhitePawn(player1), player1)
        )

        val initMoves: List<Move2D> = listOf(
            BasicMove2D(Coordinate2D(4, 1), Coordinate2D(4, 3), StandardWhitePawn(player1), player1),
            BasicMove2D(Coordinate2D(3, 6), Coordinate2D(3, 4), StandardBlackPawn(player2), player2),
            BasicMove2D(Coordinate2D(4, 3), Coordinate2D(4, 4), StandardWhitePawn(player1), player1),
            BasicMove2D(Coordinate2D(1, 6), Coordinate2D(1, 4), StandardBlackPawn(player2), player2),
            CompositeMove2D(enPassantMove, player1)
        )

        for (move in initMoves) {
            mockStandardChess.makeMove(move)
        }

        assert(board.getPiece(Coordinate2D(3, 5)) is StandardWhitePawn)
        assert(board.getPiece(Coordinate2D(3, 4)) == null)
    }
}
