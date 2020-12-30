import coordinates.Coordinate2D
import gameMoves.GameMove2D
import io.mockk.MockKAnnotations
import io.mockk.spyk
import notationFormatter.ChessNotationInput
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.King
import pieces.chess.Rook
import pieces.chess.StandardWhitePawn
import players.Player

class testChessNotationInput {
    val chessNotationInput = spyk<ChessNotationInput>()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun testValidStrToCoordinate() {
        Assertions.assertEquals(Coordinate2D(0, 4), chessNotationInput.strToCoordinate("a5"))
        Assertions.assertEquals(Coordinate2D(3, 0), chessNotationInput.strToCoordinate("d1"))
        Assertions.assertEquals(Coordinate2D(5, 7), chessNotationInput.strToCoordinate("f8"))
    }

    @Test
    fun testInvalidStrToCoordinate() {
        Assertions.assertNull(chessNotationInput.strToCoordinate("3a"))
        Assertions.assertNull(chessNotationInput.strToCoordinate("random"))
        Assertions.assertNull(chessNotationInput.strToCoordinate("88"))
        Assertions.assertNull(chessNotationInput.strToCoordinate("aa"))
    }

    @Test
    fun testCoordinateToStr() {
        Assertions.assertEquals("a5", chessNotationInput.coordinateToStr(Coordinate2D(0, 4)))
        Assertions.assertEquals("d1", chessNotationInput.coordinateToStr(Coordinate2D(3, 0)))
        Assertions.assertEquals("f8", chessNotationInput.coordinateToStr(Coordinate2D(5, 7)))
    }

    @Test
    fun testBasicGameMoveToStr() {
        val player1 = Player()
        val basicMove = GameMove2D.BasicGameMove(Coordinate2D(4, 1), Coordinate2D(4, 2), StandardWhitePawn(player1), player1)
        Assertions.assertEquals("P moves from e2 to e3", chessNotationInput.gameMoveToStr(basicMove))
    }

    @Test
    fun testCompositeGameMoveToStr() {
        val player1 = Player()
        val compositeMove = GameMove2D.CompositeGameMove(
            gameMoves = listOf(
                GameMove2D.BasicGameMove(from = Coordinate2D(4, 0), to = Coordinate2D(5, 0), pieceMoved = King(player = player1), player = player1, pieceCaptured = null, piecePromotedTo = null, checkForCheck = true),
                GameMove2D.BasicGameMove(from = Coordinate2D(5, 0), to = Coordinate2D(6, 0), pieceMoved = King(player = player1), player = player1, pieceCaptured = null, piecePromotedTo = null, checkForCheck = true),
                GameMove2D.BasicGameMove(from = Coordinate2D(7, 0), to = Coordinate2D(5, 0), pieceMoved = Rook(player = player1), player = player1, pieceCaptured = null, piecePromotedTo = null, checkForCheck = true)
            ),
            player = player1
        )
        Assertions.assertEquals("K moves from e1 to f1, K moves from f1 to g1, R moves from h1 to f1", chessNotationInput.gameMoveToStr(compositeMove))
    }
}
