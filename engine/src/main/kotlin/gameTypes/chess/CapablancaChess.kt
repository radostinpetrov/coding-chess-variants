package gameTypes.chess

import boards.Board2D
import endconditions.StandardEndConditions
import pieces.chess.*
import utils.FenUtility
import rules.CapablancaCastling
import rules.Enpassant

/**
 * Represents Capablanca Chess
 */
open class CapablancaChess : AbstractChess2D(listOf(CapablancaCastling(), Enpassant()), listOf(StandardEndConditions())) {
    override val board = Board2D(8, 10)
    override val name = "Capablanca Chess"

    override fun initBoard() {
        val fen = FenUtility("rncbqkbmnr/pppppppppp/10/10/10/10/PPPPPPPPPP/RNCBQKBMNR")
        fen.extendFenPieces('c', ::Cardinal)
        fen.extendFenPieces('m', ::Marshal)
        fen.extendFenPiecesCaseSensitive('p', ::CapablancaWhitePawn, ::CapablancaBlackPawn)
        fen.initBoardWithFEN(board, players[0], players[1])
    }
}
