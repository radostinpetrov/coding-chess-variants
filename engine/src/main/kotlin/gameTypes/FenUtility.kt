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
import java.lang.Exception

class FenUtility(val string: String) {

    private val fields : List<String> = string.split(" ")
    private val piecePlacement: String
    val activeColour: Int
    private val castling: String

    init {
        if (fields.size != 3) {
            throw IllegalArgumentException("Wrong number of fields in FEN. Expected: 3 Actual: ${fields.size}")
        }
        piecePlacement = fields[0]

        if (fields[1].single() != 'b' && fields[1].single() != 'w') {
            throw IllegalArgumentException("Wrong argument for active colour in FEN. Expected: 'b' or 'w' Actual: ${fields[1]}")
        }
        activeColour = if (fields[1].single() == 'w') 0 else 1

        // if (!fields[2].contains('Q') && !fields[2].contains('q') && !fields[2].contains('K') && !fields[2].contains('k') && !fields[2].contains('-')) {
        //     throw IllegalArgumentException("Wrong argument for castling availability FEN.")
        // }

        if (!"""(-|K?Q?k?q?)""".toRegex().matches(fields[2])) {
            throw IllegalArgumentException("Wrong argument for castling availability FEN.")
        }
        castling = fields[2]
    }

    val p1CanCastleLeft = castling.contains("Q")
    val p1CanCastleRight = castling.contains("K")
    val p2CanCastleLeft = castling.contains("q")
    val p2CanCastleRight = castling.contains("k")

    fun initBoardWithFEN(board: Board2D, player1: Player, player2: Player) {

        val rows = piecePlacement.split("/")

        if (rows.size != board.rows) {
            throw IllegalArgumentException("Wrong number of rows in piece placement FEN. Expected: ${board.rows} Actual: ${rows.size}")
        } else {

            var y = board.rows - 1

            for (row in rows) {
                var x = 0
                for (char in row) {
                    if (char.isDigit()) {
                        x += char.toString().toInt()

                        if (x > board.cols) {
                            throw IllegalArgumentException("Wrong number of columns in piece placement FEN. Expected: ${board.cols} Actual: ${x + 1}")
                        }

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
                            throw IllegalArgumentException("Wrong number of columns in piece placement FEN. Expected: ${board.cols} Actual: ${x + 1}")
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