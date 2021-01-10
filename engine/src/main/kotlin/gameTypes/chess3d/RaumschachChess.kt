package gameTypes.chess3d

import boards.Board3D
import coordinates.Coordinate3D
import pieces.chess3d.Rook3D
import pieces.chess3d.StandardBlackPawn3D
import pieces.chess3d.StandardWhitePawn3D

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
    }
}
