package gameTypes.hex
import boards.BoardHex
import coordinates.Coordinate2D
import endconditions.StandardEndConditions
import pieces.hex.*
import pieces.hex.*
import regions.CoordinateRegion
import rules.CapablancaCastling
import rules.Enpassant

/**
 * Represents Capablanca Chess
 */
open class HexagonalChess : AbstractChessHex() {
    override val board = BoardHex(6, 11)
    override val name = "Hexagonal Chess"

    override fun initBoard() {
        val player1 = players[0]
        val player2 = players[1]

        board.addPiece(Coordinate2D(1,4), WhitePawn(player1))
        board.addPiece(Coordinate2D(2,5), WhitePawn(player1))
        board.addPiece(Coordinate2D(3,6), WhitePawn(player1))
        board.addPiece(Coordinate2D(4,7), WhitePawn(player1))
        board.addPiece(Coordinate2D(5,8), WhitePawn(player1))
        board.addPiece(Coordinate2D(6,7), WhitePawn(player1))
        board.addPiece(Coordinate2D(7,6), WhitePawn(player1))
        board.addPiece(Coordinate2D(8,5), WhitePawn(player1))
        board.addPiece(Coordinate2D(9,4), WhitePawn(player1))

        board.addPiece(Coordinate2D(1,16), BlackPawn(player2))
        board.addPiece(Coordinate2D(2,15), BlackPawn(player2))
        board.addPiece(Coordinate2D(3,14), BlackPawn(player2))
        board.addPiece(Coordinate2D(4,13), BlackPawn(player2))
        board.addPiece(Coordinate2D(5,12), BlackPawn(player2))
        board.addPiece(Coordinate2D(6,13), BlackPawn(player2))
        board.addPiece(Coordinate2D(7,14), BlackPawn(player2))
        board.addPiece(Coordinate2D(8,15), BlackPawn(player2))
        board.addPiece(Coordinate2D(9,16), BlackPawn(player2))


        board.addPiece(Coordinate2D(2, 3), Rook(player1))
        board.addPiece(Coordinate2D(8, 3), Rook(player1))
        board.addPiece(Coordinate2D(2, 17), Rook(player2))
        board.addPiece(Coordinate2D(8, 17), Rook(player2))
        board.addPiece(Coordinate2D(3, 2), Knight(player1))
        board.addPiece(Coordinate2D(7, 2), Knight(player1))
        board.addPiece(Coordinate2D(3, 18), Knight(player2))
        board.addPiece(Coordinate2D(7, 18), Knight(player2))
        board.addPiece(Coordinate2D(5, 0), Bishop(player1))
        board.addPiece(Coordinate2D(5, 2), Bishop(player1))
        board.addPiece(Coordinate2D(5, 4), Bishop(player1))
        board.addPiece(Coordinate2D(5, 20), Bishop(player2))
        board.addPiece(Coordinate2D(5, 18), Bishop(player2))
        board.addPiece(Coordinate2D(5, 16), Bishop(player2))
        board.addPiece(Coordinate2D(6, 1), King(player1))
        board.addPiece(Coordinate2D(6, 19), King(player2))
        board.addPiece(Coordinate2D(4, 1), Queen(player1))
        board.addPiece(Coordinate2D(4, 19), Queen(player2))
    }
}