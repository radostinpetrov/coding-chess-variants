package gameTypes.chess

import boards.Board2D
import utils.FenUtility
import rules.Enpassant
import rules.StandardCastling
import winconditions.StandardWinConditions

open class StandardChess(val fen: FenUtility = FenUtility("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq")) : AbstractChess(mutableListOf(StandardCastling(fen.p1CanCastleLeft, fen.p1CanCastleRight, fen.p2CanCastleLeft, fen.p2CanCastleRight), Enpassant()), listOf(StandardWinConditions()), fen.activeColour) {
    override val board = Board2D(8, 8)

    override fun initGame() {
        val player1 = players[0]
        val player2 = players[1]
        fen.initBoardWithFEN(board, player1, player2)
    }
}
