package utils

import boards.Board2D
import coordinates.Coordinate2D
import gameTypes.checkers.Checkers
import gameTypes.chess.AntiChess
import gameTypes.chess.BalbosGame
import gameTypes.chess.CapablancaChess
import gameTypes.chess.Chess960
import gameTypes.chess.GrandChess
import gameTypes.chess.MiniChess
import gameTypes.chess.StandardChess
import gameTypes.xiangqi.Janggi
import gameTypes.xiangqi.Xiangqi
import pieces.Piece2D
import pieces.chess.*
import players.Player
import kotlin.reflect.KFunction1


/**
 * FEN notation utility
 * This class is able to decode a FEN string and return the piece placement and status of the game.
 */
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
    var activeColour: Int = 0
    private var castling: String = "KQkq"

    val p1CanCastleLeft: Boolean
        get() = castling.contains('Q')
    val p1CanCastleRight: Boolean
        get() = castling.contains('K')
    val p2CanCastleLeft: Boolean
        get() = castling.contains('q')
    val p2CanCastleRight: Boolean
        get() = castling.contains('k')

    private val mappedPieces: MutableMap<Char, KFunction1<Player, Piece2D>> = mutableMapOf(
        'r' to ::Rook,
        'R' to ::Rook,
        'n' to ::Knight,
        'N' to ::Knight,
        'b' to ::Bishop,
        'B' to ::Bishop,
        'q' to ::Queen,
        'Q' to ::Queen,
        'k' to ::King,
        'K' to ::King,
    )

    /**
     * Checks the FEN string to see if it is valid.
     * @throws IllegalArgumentException the string is invalid.
     */
    init {

        if (fields.isEmpty()) {
            throw IllegalArgumentException("No params given to FEN.")
        }
        piecePlacement = fields[0]

        if (fields.size >= 2) {
            if (fields[1].single() != 'b' && fields[1].single() != 'w') {
                throw IllegalArgumentException("Wrong argument for active colour in FEN. Expected: 'b' or 'w' Actual: ${fields[1]}")
            }
            activeColour = if (fields[1].single() == 'w') 0 else 1
        }

        if (fields.size == 3) {
            if ( !"""(-|K?Q?k?q?)""".toRegex().matches(fields[2])) {
                throw IllegalArgumentException("Wrong argument for castling availability FEN.")
            }
            castling = fields[2]
        }

        if (fields.size > 3) {
            throw IllegalArgumentException("Too many params given to FEN.")
        }
    }

    /**
     * Initialises a given board with the piece placement in the FEN string.
     * @throws IllegalArgumentException if the piece placement does not match the dimensions of the board.
     */
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
                } else {
                    val pieceConstructor = mappedPieces[char]
                    val player = if (char.isUpperCase()) player1 else player2

                    var piece: Piece2D
                    if (pieceConstructor != null) {
                        piece = pieceConstructor.invoke(player)
                    } else {
                        if (char == 'P') {
                            piece = WhitePawn(player1, whiteStartingRow, whitePromotionRow, whitePawnPromotions)
                        } else if (char == 'p') {
                            piece = BlackPawn(player2, blackStartingRow, blackPromotionRow, blackPawnPromotions)
                        } else {
                            throw IllegalArgumentException("Char: $char is not in FEN mappedPieces")
                        }
                    }

                    if (x >= board.cols) {
                        throw IllegalArgumentException("Wrong number of columns in piece placement FEN. Expected: ${board.cols} Actual: ${x + 1}")
                    } else {
                        board.addPiece(Coordinate2D(x, y), piece)
                        x += 1
                    }
                }
            }
            y -= 1
        }
    }

    /**
     * Adds new mapping from char to piece.
     * @param char the char to change mapping
     * @param piece the piece to add to mapping
     */
    fun extendFenPieces(char: Char, piece: KFunction1<Player, Piece2D>) {
        mappedPieces[char.toLowerCase()] = piece
        mappedPieces[char.toUpperCase()] = piece
    }

    /**
     * Adds new mapping from char to piece case sensitive.
     * @param char the char to change mapping
     * @param whitePiece the first piece to add to mapping
     * @param blackPiece the second piece to add to mapping
     */
    fun extendFenPiecesCaseSensitive(char: Char, whitePiece: KFunction1<Player, Piece2D>, blackPiece: KFunction1<Player, Piece2D>) {
        mappedPieces[char.toLowerCase()] = blackPiece
        mappedPieces[char.toUpperCase()] = whitePiece
    }
}
