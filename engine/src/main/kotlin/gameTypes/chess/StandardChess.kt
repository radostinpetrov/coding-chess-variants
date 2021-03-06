package gameTypes.chess

import boards.Board2D
import utils.FenUtility
import rules.Enpassant
import rules.StandardCastling
import endconditions.StandardEndConditions

/**
 * Represents Standard Chess
 *
 * @property fen FEN notation utility to allow easier piece placement.
 */
open class StandardChess(val fen: FenUtility = FenUtility("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq")) : AbstractChess2D(mutableListOf(StandardCastling(fen.p1CanCastleLeft, fen.p1CanCastleRight, fen.p2CanCastleLeft, fen.p2CanCastleRight), Enpassant()), listOf(StandardEndConditions()), fen.activeColour) {
    override val board = Board2D(8, 8)
    override val name = "Standard Chess"
    override fun initBoard() {
        val player1 = players[0]
        val player2 = players[1]
        fen.initBoardWithFEN(board, player1, player2)
    }
}
