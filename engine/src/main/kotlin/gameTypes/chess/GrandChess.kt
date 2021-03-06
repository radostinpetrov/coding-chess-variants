package gameTypes.chess

import boards.Board2D
import endconditions.StandardEndConditions
import pieces.chess.*
import rules.Enpassant
import utils.FenUtility

/**
 * Represents Grand Chess
 */
class GrandChess : AbstractChess2D(listOf(Enpassant()), listOf(StandardEndConditions())) {
    override val board = Board2D(10, 10)
    override val name = "Grand Chess"

    override fun initBoard() {
        val fen = FenUtility("r8r/1nbqkmcbn1/pppppppppp/10/10/10/10/PPPPPPPPPP/1NBQKMCBN1/R8R")
        fen.extendFenPieces('c', ::Cardinal)
        fen.extendFenPieces('m', ::Marshal)
        fen.extendFenPiecesCaseSensitive('p', ::GrandWhitePawn, ::GrandBlackPawn)
        fen.initBoardWithFEN(board, players[0], players[1])
    }
}