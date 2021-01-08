package testGameTypes

import coordinates.Coordinate2D
import gameTypes.chess.BalbosGame
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.*

class BalbosGameTest {
    private var mockBalbosGame = spyk<BalbosGame>()

    private val board = mockBalbosGame.board

    val player1 = mockBalbosGame.players[0]
    val player2 = mockBalbosGame.players[1]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun boardIsInitiliased() {
        val initPiecesList = listOf(
            // Pawns
            Pair(BalbosGame.BalboWhitePawn(player1), Coordinate2D(2, 2)),
            Pair(BalbosGame.BalboWhitePawn(player1), Coordinate2D(3, 2)),
            Pair(BalbosGame.BalboWhitePawn(player1), Coordinate2D(4, 2)),
            Pair(BalbosGame.BalboWhitePawn(player1), Coordinate2D(5, 2)),
            Pair(BalbosGame.BalboWhitePawn(player1), Coordinate2D(6, 2)),
            Pair(BalbosGame.BalboWhitePawn(player1), Coordinate2D(7, 2)),
            Pair(BalbosGame.BalboWhitePawn(player1), Coordinate2D(8, 2)),
            Pair(BalbosGame.BalboBlackPawn(player2), Coordinate2D(2, 7)),
            Pair(BalbosGame.BalboBlackPawn(player2), Coordinate2D(3, 7)),
            Pair(BalbosGame.BalboBlackPawn(player2), Coordinate2D(4, 7)),
            Pair(BalbosGame.BalboBlackPawn(player2), Coordinate2D(5, 7)),
            Pair(BalbosGame.BalboBlackPawn(player2), Coordinate2D(6, 7)),
            Pair(BalbosGame.BalboBlackPawn(player2), Coordinate2D(7, 7)),
            Pair(BalbosGame.BalboBlackPawn(player2), Coordinate2D(8, 7)),
            // Rooks
            Pair(Rook(player1), Coordinate2D(3, 1)),
            Pair(Rook(player1), Coordinate2D(7, 1)),
            Pair(Rook(player2), Coordinate2D(3, 8)),
            Pair(Rook(player2), Coordinate2D(7, 8)),
            // Knights
            Pair(Knight(player1), Coordinate2D(4, 1)),
            Pair(Knight(player1), Coordinate2D(6, 1)),
            Pair(Knight(player2), Coordinate2D(4, 8)),
            Pair(Knight(player2), Coordinate2D(6, 8)),
            // Bishops
            Pair(Bishop(player1), Coordinate2D(5, 0)),
            Pair(Bishop(player1), Coordinate2D(5, 1)),
            Pair(Bishop(player2), Coordinate2D(5, 8)),
            Pair(Bishop(player2), Coordinate2D(5, 9)),
            // Queens
            Pair(Queen(player1), Coordinate2D(6, 0)),
            Pair(Queen(player2), Coordinate2D(6, 9)),
            // Kings
            Pair(King(player1), Coordinate2D(4, 0)),
            Pair(King(player2), Coordinate2D(4, 9))
        )

        mockBalbosGame.initGame()
        val initPieces = board.getPieces()
        assertTrue(initPieces.containsAll(initPiecesList))
        assertEquals(initPieces.size, initPiecesList.size)
    }
}
