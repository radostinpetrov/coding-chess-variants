package gameTypes.xiangqi

import coordinates.Coordinate2D
import boards.Board2D
import gameTypes.chess.AbstractChess
import endconditions.StandardEndConditions
import rules.GeneralsRule
import pieces.janggi.* // ktlint-disable no-wildcard-imports
import pieces.janggi.Elephant
import utils.FenUtility

/**
 * Represents Janggi (Korean Chess)
 */
class Janggi : AbstractChess(listOf(GeneralsRule()), listOf(StandardEndConditions())) {
    override val board = Board2D(10, 9)
    override val name = "Janggi"

    override fun initBoard() {
        val fen = FenUtility("jhea1aehj/4g4/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/4G4/JHEA1AEHJ")
        fen.extendFenPieces('j', ::Chariot)
        fen.extendFenPieces('c', ::Cannon)
        fen.extendFenPieces('h', ::Horse)
        fen.extendFenPieces('e', ::Elephant)
        fen.extendFenPieces('a', ::Advisor)
        fen.extendFenPieces('g', ::General)
        fen.extendFenPiecesCaseSensitive('s', ::RedSoldier, ::BlueSoldier)
        fen.initBoardWithFEN(board, players[0], players[1])
    }
}
