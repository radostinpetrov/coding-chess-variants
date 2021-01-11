package gameTypes.hex
import boards.BoardHex
import coordinates.Coordinate2D
import pieces.hex.*

/**
 * Represents Capablanca Chess
 */
open class HexagonalChess : AbstractChessHex() {
    override val board = BoardHex(6, 11)
    override val name = "Hexagonal Chess"

    override fun initBoard() {
        val player1 = players[0]
        val player2 = players[1]

        board.addPiece(Coordinate2D(1,4), HexWhitePawn(player1))
        board.addPiece(Coordinate2D(2,5), HexWhitePawn(player1))
        board.addPiece(Coordinate2D(3,6), HexWhitePawn(player1))
        board.addPiece(Coordinate2D(4,7), HexWhitePawn(player1))
        board.addPiece(Coordinate2D(5,8), HexWhitePawn(player1))
        board.addPiece(Coordinate2D(6,7), HexWhitePawn(player1))
        board.addPiece(Coordinate2D(7,6), HexWhitePawn(player1))
        board.addPiece(Coordinate2D(8,5), HexWhitePawn(player1))
        board.addPiece(Coordinate2D(9,4), HexWhitePawn(player1))

        board.addPiece(Coordinate2D(1,16), HexBlackPawn(player2))
        board.addPiece(Coordinate2D(2,15), HexBlackPawn(player2))
        board.addPiece(Coordinate2D(3,14), HexBlackPawn(player2))
        board.addPiece(Coordinate2D(4,13), HexBlackPawn(player2))
        board.addPiece(Coordinate2D(5,12), HexBlackPawn(player2))
        board.addPiece(Coordinate2D(6,13), HexBlackPawn(player2))
        board.addPiece(Coordinate2D(7,14), HexBlackPawn(player2))
        board.addPiece(Coordinate2D(8,15), HexBlackPawn(player2))
        board.addPiece(Coordinate2D(9,16), HexBlackPawn(player2))


        board.addPiece(Coordinate2D(2, 3), HexRook(player1))
        board.addPiece(Coordinate2D(8, 3), HexRook(player1))
        board.addPiece(Coordinate2D(2, 17), HexRook(player2))
        board.addPiece(Coordinate2D(8, 17), HexRook(player2))
        board.addPiece(Coordinate2D(3, 2), HexKnight(player1))
        board.addPiece(Coordinate2D(7, 2), HexKnight(player1))
        board.addPiece(Coordinate2D(3, 18), HexKnight(player2))
        board.addPiece(Coordinate2D(7, 18), HexKnight(player2))
        board.addPiece(Coordinate2D(5, 0), HexBishop(player1))
        board.addPiece(Coordinate2D(5, 2), HexBishop(player1))
        board.addPiece(Coordinate2D(5, 4), HexBishop(player1))
        board.addPiece(Coordinate2D(5, 20), HexBishop(player2))
        board.addPiece(Coordinate2D(5, 18), HexBishop(player2))
        board.addPiece(Coordinate2D(5, 16), HexBishop(player2))
        board.addPiece(Coordinate2D(6, 1), HexKing(player1))
        board.addPiece(Coordinate2D(6, 19), HexKing(player2))
        board.addPiece(Coordinate2D(4, 1), HexQueen(player1))
        board.addPiece(Coordinate2D(4, 19), HexQueen(player2))
    }
}