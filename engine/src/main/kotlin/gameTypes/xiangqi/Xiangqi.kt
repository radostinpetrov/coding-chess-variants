package gameTypes.xiangqi

import boards.Board2D
import coordinates.Coordinate2D
import gameTypes.chess.AbstractChess
import endconditions.StandardEndConditions
import rules.GeneralsRule
import pieces.xiangqi.* // ktlint-disable no-wildcard-imports
import utils.FenUtility

/**
 * Represents Xiangqi (Chinese Chess)
 */
class Xiangqi : AbstractChess(listOf(GeneralsRule()), listOf(StandardEndConditions())) {
    override val board = Board2D(10, 9)
    override val name = "Xiangqi"

    override fun initBoard() {
        val fen = FenUtility("jheagaehj/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/JHEAGAEHJ")
        fen.extendFenPieces('j', ::XiangqiChariot)
        fen.extendFenPieces('c', ::XiangqiCannon)
        fen.extendFenPieces('h', ::XiangqiHorse)
        fen.extendFenPieces('a', ::XiangqiAdvisor)
        fen.extendFenPieces('g', ::XiangqiGeneral)
        fen.extendFenPiecesCaseSensitive('e', ::XiangqiRedElephant, ::XiangqiBlueElephant)
        fen.extendFenPiecesCaseSensitive('s', ::XiangqiRedSoldier, ::XiangqiBlueSoldier)
        fen.initBoardWithFEN(board, players[0], players[1])
    }
}
