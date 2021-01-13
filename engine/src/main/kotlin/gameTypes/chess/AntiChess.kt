package gameTypes.chess

import boards.Board2D
import rules.ForcedCaptureRule
import endconditions.AntiChessEndConditions
import pieces.antichess.AntiChessBlackPawn
import pieces.antichess.AntiChessKing
import pieces.antichess.AntiChessWhitePawn
import utils.FenUtility

/**
 * Represents Anti Chess
 */
open class AntiChess : AbstractChess2D(listOf(ForcedCaptureRule()), listOf(AntiChessEndConditions())) {
    override val board = Board2D(8, 8)
    override val name = "Anti Chess"

    override fun initBoard() {
        val fen = FenUtility("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR")
        fen.extendFenPieces('k', ::AntiChessKing)
        fen.extendFenPiecesCaseSensitive('p', ::AntiChessWhitePawn, ::AntiChessBlackPawn)
        fen.initBoardWithFEN(board, players[0], players[1])
    }
}