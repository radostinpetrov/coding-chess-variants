package testRules

import coordinates.Coordinate2D
import moves.*
import gameTypes.chess.StandardChess
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.*

class CastlingTest {

    val mockStandardChess = spyk<StandardChess>()

    val player1 = mockStandardChess.players[0]
    val player2 = mockStandardChess.players[1]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun castleRightTest() {
        mockStandardChess.initGame()

        val initMoves: List<BasicMove2D> = listOf(
            BasicMove2D(Coordinate2D(4, 1), Coordinate2D(4, 3), StandardWhitePawn(player1), player1),
            BasicMove2D(Coordinate2D(0, 6), Coordinate2D(0, 5), StandardBlackPawn(player2), player2),
            BasicMove2D(Coordinate2D(5, 0), Coordinate2D(4, 1), Bishop(player1), player1),
            BasicMove2D(Coordinate2D(1, 6), Coordinate2D(1, 5), StandardBlackPawn(player2), player2),
            BasicMove2D(Coordinate2D(6, 0), Coordinate2D(5, 2), Knight(player1), player1),
            BasicMove2D(Coordinate2D(2, 6), Coordinate2D(2, 5), StandardBlackPawn(player2), player2),
        )

        for (move in initMoves) {
            mockStandardChess.makeMove(move)
        }

        val moves = mockStandardChess.getValidMoves(player1)
        val castleMove = CompositeMove2D(
            moves = listOf(
                RemovePieceMove2D(player = player1, piece = Rook(player1), coordinate = Coordinate2D(7, 0)),
                BasicMove2D(from = Coordinate2D(4, 0), to = Coordinate2D(5, 0), pieceMoved = King(player = player1), player = player1, pieceCaptured = null, piecePromotedTo = null, checkForCheck = true),
                BasicMove2D(from = Coordinate2D(5, 0), to = Coordinate2D(6, 0), pieceMoved = King(player = player1), player = player1, pieceCaptured = null, piecePromotedTo = null, checkForCheck = true),
                AddPieceMove2D(player = player1, piece = Rook(player1), coordinate = Coordinate2D(5, 0)),
            ),
            player = player1
        )
        assertTrue(moves.contains(castleMove))
    }

    @Test
    fun castleLeftTest() {
        mockStandardChess.initGame()

        val initMoves: List<BasicMove2D> = listOf(
            BasicMove2D(Coordinate2D(3, 1), Coordinate2D(3, 3), StandardWhitePawn(player1), player1),
            BasicMove2D(Coordinate2D(0, 6), Coordinate2D(0, 5), StandardBlackPawn(player2), player2),
            BasicMove2D(Coordinate2D(3, 0), Coordinate2D(3, 2), Queen(player1), player1),
            BasicMove2D(Coordinate2D(1, 6), Coordinate2D(1, 5), StandardBlackPawn(player2), player2),
            BasicMove2D(Coordinate2D(2, 0), Coordinate2D(3, 1), Bishop(player1), player1),
            BasicMove2D(Coordinate2D(2, 6), Coordinate2D(2, 5), StandardBlackPawn(player2), player2),
            BasicMove2D(Coordinate2D(1, 0), Coordinate2D(2, 2), Knight(player1), player1),
            BasicMove2D(Coordinate2D(3, 6), Coordinate2D(3, 5), StandardBlackPawn(player2), player2)
        )

        for (move in initMoves) {
            mockStandardChess.makeMove(move)
        }

        val moves = mockStandardChess.getValidMoves(player1)
        val castleMove = CompositeMove2D(
            moves = listOf(
                RemovePieceMove2D(player = player1, piece = Rook(player1), coordinate = Coordinate2D(0, 0)),
                BasicMove2D(from = Coordinate2D(4, 0), to = Coordinate2D(3, 0), pieceMoved = King(player = player1), player = player1, pieceCaptured = null, piecePromotedTo = null, checkForCheck = true),
                BasicMove2D(from = Coordinate2D(3, 0), to = Coordinate2D(2, 0), pieceMoved = King(player = player1), player = player1, pieceCaptured = null, piecePromotedTo = null, checkForCheck = true),
                AddPieceMove2D(player = player1, piece = Rook(player1), coordinate = Coordinate2D(3, 0)),
            ),
            player = player1
        )
        assertTrue(moves.contains(castleMove))
    }
}
