package testRules

import ConsoleGameHelper
import Coordinate
import GameMove
import gameTypes.chess.StandardChess
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import pieces.chess.*
import players.Player

class CastlingTest {

    val mockStandardChess = spyk<StandardChess>()
    val board = mockStandardChess.board

    val mockHumanPlayer1 = mockk<Player>()
    val mockHumanPlayer2 = mockk<Player>()

    @Test
    fun castleRightTest() {
        mockStandardChess.addPlayer(mockHumanPlayer1)
        mockStandardChess.addPlayer(mockHumanPlayer2)
        mockStandardChess.initGame()

        val move1 = GameMove.BasicGameMove(Coordinate(4, 1), Coordinate(4, 3), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1)
        val move2 = GameMove.BasicGameMove(Coordinate(0, 6), Coordinate(0, 5), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2)
        val move3 = GameMove.BasicGameMove(Coordinate(5, 0), Coordinate(4, 1), Bishop(mockHumanPlayer1), mockHumanPlayer1)
        val move4 = GameMove.BasicGameMove(Coordinate(1, 6), Coordinate(1, 5), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2)
        val move5 = GameMove.BasicGameMove(Coordinate(6, 0), Coordinate(5, 2), Knight(mockHumanPlayer1), mockHumanPlayer1)
        val move6 = GameMove.BasicGameMove(Coordinate(2, 6), Coordinate(2, 5), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2)
//        val move7 = GameMove.BasicGameMove(Coordinate(3, 6), Coordinate(3, 5), StandardBlackPawn(mockHumanPlayer1), mockHumanPlayer2)
//        val move8 = GameMove.BasicGameMove(Coordinate(3, 6), Coordinate(4, 5), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2)
//        val move9 = GameMove.BasicGameMove(Coordinate(3, 6), Coordinate(3, 5), StandardBlackPawn(mockHumanPlayer1), mockHumanPlayer2)
//        val move10 = GameMove.BasicGameMove(Coordinate(4, 6), Coordinate(5, 5), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2)
        mockStandardChess.makeMove(move1)
        mockStandardChess.makeMove(move2)
        mockStandardChess.makeMove(move3)
        mockStandardChess.makeMove(move4)
        mockStandardChess.makeMove(move5)
        mockStandardChess.makeMove(move6)
        val moves = mockStandardChess.getValidMoves(mockHumanPlayer1)
        val castleMove = GameMove.CompositeGameMove(
            gameMoves = listOf(
                GameMove.BasicGameMove(from = Coordinate(4, 0), to = Coordinate(5, 0), pieceMoved = King(player = mockHumanPlayer1), player = mockHumanPlayer1, pieceCaptured = null, piecePromotedTo = null, checkForCheck = true),
                GameMove.BasicGameMove(from = Coordinate(5, 0), to = Coordinate(6, 0), pieceMoved = King(player = mockHumanPlayer1), player = mockHumanPlayer1, pieceCaptured = null, piecePromotedTo = null, checkForCheck = true),
                GameMove.BasicGameMove(from = Coordinate(7, 0), to = Coordinate(5, 0), pieceMoved = Rook(player = mockHumanPlayer1), player = mockHumanPlayer1, pieceCaptured = null, piecePromotedTo = null, checkForCheck = true)
            ),
            player = mockHumanPlayer1
        )
        assertTrue(moves.contains(castleMove))
    }

    @Test
    fun castleLeftTest() {
        mockStandardChess.addPlayer(mockHumanPlayer1)
        mockStandardChess.addPlayer(mockHumanPlayer2)
        mockStandardChess.initGame()

        val move1 = GameMove.BasicGameMove(Coordinate(3, 1), Coordinate(3, 3), StandardWhitePawn(mockHumanPlayer1), mockHumanPlayer1)
        val move2 = GameMove.BasicGameMove(Coordinate(0, 6), Coordinate(0, 5), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2)
        val move3 = GameMove.BasicGameMove(Coordinate(3, 0), Coordinate(3, 2), Queen(mockHumanPlayer1), mockHumanPlayer1)
        val move4 = GameMove.BasicGameMove(Coordinate(1, 6), Coordinate(1, 5), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2)
        val move5 = GameMove.BasicGameMove(Coordinate(2, 0), Coordinate(3, 1), Bishop(mockHumanPlayer1), mockHumanPlayer1)
        val move6 = GameMove.BasicGameMove(Coordinate(2, 6), Coordinate(2, 5), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2)
        val move7 = GameMove.BasicGameMove(Coordinate(1, 0), Coordinate(2, 2), Knight(mockHumanPlayer1), mockHumanPlayer1)
        val move8 = GameMove.BasicGameMove(Coordinate(3, 6), Coordinate(3, 5), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2)
//        val move9 = GameMove.BasicGameMove(Coordinate(3, 6), Coordinate(3, 5), StandardBlackPawn(mockHumanPlayer1), mockHumanPlayer2)
//        val move10 = GameMove.BasicGameMove(Coordinate(4, 6), Coordinate(4, 5), StandardBlackPawn(mockHumanPlayer2), mockHumanPlayer2)
        mockStandardChess.makeMove(move1)
        mockStandardChess.makeMove(move2)
        mockStandardChess.makeMove(move3)
        mockStandardChess.makeMove(move4)
        mockStandardChess.makeMove(move5)
        mockStandardChess.makeMove(move6)
        mockStandardChess.makeMove(move7)
        mockStandardChess.makeMove(move8)
        val console = ConsoleGameHelper(mockStandardChess)
        console.display()
        println(mockStandardChess.getCurrentPlayer())
        val moves = mockStandardChess.getValidMoves(mockHumanPlayer1)
        val castleMove = GameMove.CompositeGameMove(
            gameMoves = listOf(
                GameMove.BasicGameMove(from = Coordinate(4, 0), to = Coordinate(3, 0), pieceMoved = King(player = mockHumanPlayer1), player = mockHumanPlayer1, pieceCaptured = null, piecePromotedTo = null, checkForCheck = true),
                GameMove.BasicGameMove(from = Coordinate(3, 0), to = Coordinate(2, 0), pieceMoved = King(player = mockHumanPlayer1), player = mockHumanPlayer1, pieceCaptured = null, piecePromotedTo = null, checkForCheck = true),
                GameMove.BasicGameMove(from = Coordinate(0, 0), to = Coordinate(3, 0), pieceMoved = Rook(player = mockHumanPlayer1), player = mockHumanPlayer1, pieceCaptured = null, piecePromotedTo = null, checkForCheck = true)
            ),
            player = mockHumanPlayer1
        )
        assertTrue(moves.contains(castleMove))
    }
}
