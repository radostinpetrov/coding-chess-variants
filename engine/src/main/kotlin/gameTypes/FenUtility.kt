package gameTypes

import boards.Board2D
import coordinates.Coordinate2D
import pieces.chess.Bishop
import pieces.chess.King
import pieces.chess.Knight
import pieces.chess.Queen
import pieces.chess.Rook
import pieces.chess.StandardBlackPawn
import pieces.chess.StandardWhitePawn
import players.Player

object FenUtility {

    fun initBoardWithFEN(board: Board2D, FENString: String, player1: Player, player2: Player) {
        val rows = FENString.split("/")

        if (rows.size != board.rows) {
            //TODO fen notation is wrong
        } else {

            var y = board.rows - 1

            for (row in rows) {
                var x = 0
                for (char in row) {
                    if (char.isDigit()) {
                        x += char.toString().toInt()
                    } else {
                        val piece = when (char) {
                            'p','P'  -> if (char.isUpperCase()) StandardWhitePawn(player1) else StandardBlackPawn(player2)
                            'r','R'  -> if (char.isUpperCase()) Rook(player1) else Rook(player2)
                            'n','N'  -> if (char.isUpperCase()) Knight(player1) else Knight(player2)
                            'b','B'  -> if (char.isUpperCase()) Bishop(player1) else Bishop(player2)
                            'q','Q'  -> if (char.isUpperCase()) Queen(player1) else Queen(player2)
                            'k','K'  -> if (char.isUpperCase()) King(player1) else King(player2)
                            else -> null
                        }
                        if (x >= board.cols) {
                            //TODO fen notation is wrong
                        } else {
                            board.addPiece(Coordinate2D(x, y), piece!!)
                            x += 1
                        }
                    }
                }
                y -= 1
            }
        }
    }
}