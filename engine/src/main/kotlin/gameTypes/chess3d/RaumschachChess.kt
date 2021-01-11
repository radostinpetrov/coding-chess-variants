package gameTypes.chess3d

import boards.Board3D
import coordinates.Coordinate3D
import pieces.chess3d.*
/**
 * Represents Raumschach Chess, a 5x5x5 3D chess game
 */
class RaumschachChess : AbstractChess3D() {
    override val board = Board3D(5, 5, 5)
    override val name = "Raumschach Chess"
    override fun initBoard() {
        val player1 = players[0]
        val player2 = players[1]
        for (z in 0..1) {
            board.addPiece(Coordinate3D(0, 1, z), StandardWhitePawn3D(player1))
            board.addPiece(Coordinate3D(1, 1, z), StandardWhitePawn3D(player1))
            board.addPiece(Coordinate3D(2, 1, z), StandardWhitePawn3D(player1))
            board.addPiece(Coordinate3D(3, 1, z), StandardWhitePawn3D(player1))
            board.addPiece(Coordinate3D(4, 1, z), StandardWhitePawn3D(player1))
            board.addPiece(Coordinate3D(0, 3, z + 3), StandardBlackPawn3D(player2))
            board.addPiece(Coordinate3D(1, 3, z + 3), StandardBlackPawn3D(player2))
            board.addPiece(Coordinate3D(2, 3, z + 3), StandardBlackPawn3D(player2))
            board.addPiece(Coordinate3D(3, 3, z + 3), StandardBlackPawn3D(player2))
            board.addPiece(Coordinate3D(4, 3, z + 3), StandardBlackPawn3D(player2))
        }
        board.addPiece(Coordinate3D(0, 0, 0), Rook3D(player1))
        board.addPiece(Coordinate3D(4, 0, 0), Rook3D(player1))
        board.addPiece(Coordinate3D(0, 4, 4), Rook3D(player2))
        board.addPiece(Coordinate3D(4, 4, 4), Rook3D(player2))
        board.addPiece(Coordinate3D(1, 0, 0), Knight3D(player1))
        board.addPiece(Coordinate3D(3, 0, 0), Knight3D(player1))
        board.addPiece(Coordinate3D(1, 4, 4), Knight3D(player2))
        board.addPiece(Coordinate3D(3, 4, 4), Knight3D(player2))
        board.addPiece(Coordinate3D(1, 0, 1), Unicorn3D(player1))
        board.addPiece(Coordinate3D(4, 0, 1), Unicorn3D(player1))
        board.addPiece(Coordinate3D(1, 4, 3), Unicorn3D(player2))
        board.addPiece(Coordinate3D(4, 4, 3), Unicorn3D(player2))
        board.addPiece(Coordinate3D(0, 0, 1), Bishop3D(player1))
        board.addPiece(Coordinate3D(3, 0, 1), Bishop3D(player1))
        board.addPiece(Coordinate3D(0, 4, 3), Bishop3D(player2))
        board.addPiece(Coordinate3D(3, 4, 3), Bishop3D(player2))
        board.addPiece(Coordinate3D(2, 0, 1), Queen3D(player1))
        board.addPiece(Coordinate3D(2, 4, 3), Queen3D(player2))
        board.addPiece(Coordinate3D(2, 0, 0), King3D(player1))
        board.addPiece(Coordinate3D(2, 4, 4), King3D(player2))
    }
}
