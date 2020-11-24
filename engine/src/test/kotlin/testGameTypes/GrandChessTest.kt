package testGameTypes

import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.spyk
import main.kotlin.Coordinate
import main.kotlin.gameTypes.chess.CapablancaChess
import main.kotlin.gameTypes.chess.GrandChess
import main.kotlin.pieces.chess.*
import main.kotlin.players.HumanPlayer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GrandChessTest {

    private var mockGrandChess = spyk<GrandChess>()

    private val board = mockGrandChess.board

    val mockHumanPlayer1 = mockk<HumanPlayer>()
    val mockHumanPlayer2 = mockk<HumanPlayer>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun boardIsInitialized() {

        val initPiecesTest = listOf(
            //Pawns
            Pair(GrandWhitePawn(mockHumanPlayer1), Coordinate(0, 2)),
            Pair(GrandWhitePawn(mockHumanPlayer1), Coordinate(1, 2)),
            Pair(GrandWhitePawn(mockHumanPlayer1), Coordinate(2, 2)),
            Pair(GrandWhitePawn(mockHumanPlayer1), Coordinate(3, 2)),
            Pair(GrandWhitePawn(mockHumanPlayer1), Coordinate(4, 2)),
            Pair(GrandWhitePawn(mockHumanPlayer1), Coordinate(5, 2)),
            Pair(GrandWhitePawn(mockHumanPlayer1), Coordinate(6, 2)),
            Pair(GrandWhitePawn(mockHumanPlayer1), Coordinate(7, 2)),
            Pair(GrandWhitePawn(mockHumanPlayer1), Coordinate(8, 2)),
            Pair(GrandWhitePawn(mockHumanPlayer1), Coordinate(9, 2)),

            Pair(GrandBlackPawn(mockHumanPlayer2), Coordinate(0, 7)),
            Pair(GrandBlackPawn(mockHumanPlayer2), Coordinate(1, 7)),
            Pair(GrandBlackPawn(mockHumanPlayer2), Coordinate(2, 7)),
            Pair(GrandBlackPawn(mockHumanPlayer2), Coordinate(3, 7)),
            Pair(GrandBlackPawn(mockHumanPlayer2), Coordinate(4, 7)),
            Pair(GrandBlackPawn(mockHumanPlayer2), Coordinate(5, 7)),
            Pair(GrandBlackPawn(mockHumanPlayer2), Coordinate(6, 7)),
            Pair(GrandBlackPawn(mockHumanPlayer2), Coordinate(7, 7)),
            Pair(GrandBlackPawn(mockHumanPlayer2), Coordinate(8, 7)),
            Pair(GrandBlackPawn(mockHumanPlayer2), Coordinate(9, 7)),
            // Rooks
            Pair(Rook(mockHumanPlayer1), Coordinate(0, 0)),
            Pair(Rook(mockHumanPlayer1), Coordinate(9, 0)),
            Pair(Rook(mockHumanPlayer2), Coordinate(0, 9)),
            Pair(Rook(mockHumanPlayer2), Coordinate(9, 9)),
            // Knights
            Pair(Knight(mockHumanPlayer1), Coordinate(1, 1)),
            Pair(Knight(mockHumanPlayer1), Coordinate(8, 1)),
            Pair(Knight(mockHumanPlayer2), Coordinate(1, 8)),
            Pair(Knight(mockHumanPlayer2), Coordinate(8, 8)),
            // Bishops
            Pair(Bishop(mockHumanPlayer1), Coordinate(2, 1)),
            Pair(Bishop(mockHumanPlayer1), Coordinate(7, 1)),
            Pair(Bishop(mockHumanPlayer2), Coordinate(2, 8)),
            Pair(Bishop(mockHumanPlayer2), Coordinate(7, 8)),
            // Cardinals
            Pair(Cardinal(mockHumanPlayer1), Coordinate(6, 1)),
            Pair(Cardinal(mockHumanPlayer2), Coordinate(6, 8)),
            // Marshals
            Pair(Marshal(mockHumanPlayer1), Coordinate(5, 1)),
            Pair(Marshal(mockHumanPlayer2), Coordinate(5, 8)),
            // Queens
            Pair(Queen(mockHumanPlayer1), Coordinate(3, 1)),
            Pair(Queen(mockHumanPlayer2), Coordinate(3, 8)),
            // Kings
            Pair(King(mockHumanPlayer1), Coordinate(4, 1)),
            Pair(King(mockHumanPlayer2), Coordinate(4, 8))
        )
        mockGrandChess.addPlayer(mockHumanPlayer1)
        mockGrandChess.addPlayer(mockHumanPlayer2)
        mockGrandChess.initGame()
        val initPieces = board.getPieces()
        Assertions.assertTrue(initPieces.containsAll(initPiecesTest))
        Assertions.assertEquals(initPieces.size, initPiecesTest.size)
    }
}