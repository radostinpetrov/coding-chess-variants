package gameTypes

import boards.Board2D
import coordinates.Coordinate2D
import pieces.Piece
import pieces.Piece2D
import pieces.chess.*
import players.Player
import kotlin.reflect.KFunction1

class FenUtility(
    val string: String,
    val whiteStartingRow: Int = 1,
    val whitePromotionRow: Int = 7,
    val blackStartingRow: Int = 6,
    val blackPromotionRow: Int = 0,
    val pawnPromotions: List<KFunction1<Player, Piece2D>> = listOf(::Queen, ::Bishop, ::Knight, ::Rook)
) {
    private val fields: List<String> = string.split(" ")
    private val piecePlacement: String
    val activeColour: Int
    private val castling: String

    val p1CanCastleLeft: Boolean
    val p1CanCastleRight: Boolean
    val p2CanCastleLeft: Boolean
    val p2CanCastleRight: Boolean

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

        p1CanCastleLeft =  castling.contains("Q")
        p1CanCastleRight = castling.contains("K")
        p2CanCastleLeft =  castling.contains("q")
        p2CanCastleRight = castling.contains("k")
    }



//    fun mapSymbol(symbol: Character, )

    fun initBoardWithFEN(board: Board2D, player1: Player, player2: Player) {

        val rows = piecePlacement.split("/")

        if (rows.size != board.rows) {
            throw IllegalArgumentException("Wrong number of rows in piece placement FEN. Expected: ${board.rows} Actual: ${rows.size}")
        }

        var y = board.rows - 1

        val whitePawnPromotions = pawnPromotions.map { it(player1) }
        val blackPawnPromotions = pawnPromotions.map { it(player2) }

        for (row in rows) {
            var x = 0
            for (char in row) {
                if (char.isDigit()) {
                    x += char.toString().toInt()

                    if (x > board.cols) {
                        throw IllegalArgumentException("Wrong number of columns in piece placement FEN. Expected: ${board.cols} Actual: ${x + 1}")
                    }
                }
                val piece = when (char) {
                    'p', 'P' -> if (char.isUpperCase()) WhitePawn(player1, whiteStartingRow, whitePromotionRow, whitePawnPromotions) else BlackPawn(player2, blackStartingRow, blackPromotionRow, blackPawnPromotions)
                    'r', 'R' -> if (char.isUpperCase()) Rook(player1) else Rook(player2)
                    'n', 'N' -> if (char.isUpperCase()) Knight(player1) else Knight(player2)
                    'b', 'B' -> if (char.isUpperCase()) Bishop(player1) else Bishop(player2)
                    'q', 'Q' -> if (char.isUpperCase()) Queen(player1) else Queen(player2)
                    'k', 'K' -> if (char.isUpperCase()) King(player1) else King(player2)
                    else -> null
                }
                if (x >= board.cols) {
                    throw IllegalArgumentException("Wrong number of columns in piece placement FEN. Expected: ${board.cols} Actual: ${x + 1}")
                } else {
                    board.addPiece(Coordinate2D(x, y), piece!!)
                    x += 1
                }
            }
            y -= 1
        }
    }

    fun usePawns(any: Any, any1: Any) {
        TODO("Not yet implemented")
    }
}
