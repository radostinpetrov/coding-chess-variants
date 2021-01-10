package testRules

import coordinates.Coordinate2D
import moves.Move2D
import moves.Move2D.SimpleMove.*
import gameTypes.chess.CapablancaChess
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.*

class CapablancaCastlingTest {

    val mockStandardChess = spyk<CapablancaChess>()

    val player1 = mockStandardChess.players[0]
    val player2 = mockStandardChess.players[1]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun castleRightTest() {
        mockStandardChess.initGame()

        val initMoves: List<BasicMove> = listOf(
            BasicMove(Coordinate2D(7, 1), Coordinate2D(7, 3), CapablancaWhitePawn(player1), player1),
            BasicMove(Coordinate2D(0, 6), Coordinate2D(0, 5), CapablancaBlackPawn(player2), player2),
            BasicMove(Coordinate2D(6, 0), Coordinate2D(9, 3), Bishop(player1), player1),
            BasicMove(Coordinate2D(1, 6), Coordinate2D(1, 5), CapablancaBlackPawn(player2), player2),
            BasicMove(Coordinate2D(7, 0), Coordinate2D(8, 2), Marshal(player1), player1),
            BasicMove(Coordinate2D(2, 6), Coordinate2D(2, 5), CapablancaBlackPawn(player2), player2),
            BasicMove(Coordinate2D(8, 0), Coordinate2D(9, 2), Knight(player1), player1),
            BasicMove(Coordinate2D(3, 6), Coordinate2D(3, 5), CapablancaBlackPawn(player2), player2),
        )

        for (move in initMoves) {
            println(move)
            mockStandardChess.playerMakeMove(move)
        }

        val moves = mockStandardChess.getValidMoves(player1)
        val castleMove =
            Move2D.CompositeMove(
                moves = listOf(
                    RemovePieceMove(
                      player = player1,
                      piece = Rook(player = player1),
                      coordinate = Coordinate2D(9, 0)
                    ),
                    BasicMove(
                        from = Coordinate2D(5, 0),
                        to = Coordinate2D(6, 0),
                        pieceMoved = King(player = player1),
                        player = player1,
                        pieceCaptured = null,
                        piecePromotedTo = null,
                        checkForCheck = true
                    ),
                    BasicMove(
                        from = Coordinate2D(6, 0),
                        to = Coordinate2D(7, 0),
                        pieceMoved = King(player = player1),
                        player = player1,
                        pieceCaptured = null,
                        piecePromotedTo = null,
                        checkForCheck = true
                    ),
                    BasicMove(
                        from = Coordinate2D(7, 0),
                        to = Coordinate2D(8, 0),
                        pieceMoved = King(player = player1),
                        player = player1,
                        pieceCaptured = null,
                        piecePromotedTo = null,
                        checkForCheck = true
                    ),
                    AddPieceMove(
                        player = player1,
                        piece = Rook(player = player1),
                        coordinate = Coordinate2D(7, 0)
                    ),
                ),
                player = player1
            )
        assertTrue(moves.contains(castleMove))
    }

    @Test
    fun castleLeftTest() {
        mockStandardChess.initGame()

        val initMoves: List<BasicMove> = listOf(
            BasicMove(Coordinate2D(4, 1), Coordinate2D(4, 3), CapablancaWhitePawn(player1), player1),
            BasicMove(Coordinate2D(0, 6), Coordinate2D(0, 5), CapablancaBlackPawn(player2), player2),
            BasicMove(Coordinate2D(3, 0), Coordinate2D(7, 4), Bishop(player1), player1),
            BasicMove(Coordinate2D(1, 6), Coordinate2D(1, 5), CapablancaBlackPawn(player2), player2),
            BasicMove(Coordinate2D(4, 0), Coordinate2D(4, 2), Queen(player1), player1),
            BasicMove(Coordinate2D(2, 6), Coordinate2D(2, 5), CapablancaBlackPawn(player2), player2),
            BasicMove(Coordinate2D(2, 0), Coordinate2D(3, 2), Cardinal(player1), player1),
            BasicMove(Coordinate2D(3, 6), Coordinate2D(3, 5), CapablancaBlackPawn(player2), player2),
            BasicMove(Coordinate2D(1, 0), Coordinate2D(2, 2), Knight(player1), player1),
            BasicMove(Coordinate2D(4, 6), Coordinate2D(4, 5), CapablancaBlackPawn(player2), player2),
        )

        for (move in initMoves) {
            println(move)
            mockStandardChess.playerMakeMove(move)
        }

        val moves = mockStandardChess.getValidMoves(player1)
        val castleMove =
            Move2D.CompositeMove(
                moves = listOf(
                    RemovePieceMove(
                        player = player1,
                        piece = Rook(player = player1),
                        coordinate = Coordinate2D(0, 0)
                    ),
                    BasicMove(
                        from = Coordinate2D(5, 0),
                        to = Coordinate2D(4, 0),
                        pieceMoved = King(player = player1),
                        player = player1,
                        pieceCaptured = null,
                        piecePromotedTo = null,
                        checkForCheck = true
                    ),
                    BasicMove(
                        from = Coordinate2D(4, 0),
                        to = Coordinate2D(3, 0),
                        pieceMoved = King(player = player1),
                        player = player1,
                        pieceCaptured = null,
                        piecePromotedTo = null,
                        checkForCheck = true
                    ),
                    BasicMove(
                        from = Coordinate2D(3, 0),
                        to = Coordinate2D(2, 0),
                        pieceMoved = King(player = player1), player = player1,
                        pieceCaptured = null,
                        piecePromotedTo = null,
                        checkForCheck = true
                    ),
                    AddPieceMove(
                        player = player1,
                        piece = Rook(player = player1),
                        coordinate = Coordinate2D(3, 0)
                    ),
                ),
                player = player1
            )
        assertTrue(moves.contains(castleMove))
    }
}
