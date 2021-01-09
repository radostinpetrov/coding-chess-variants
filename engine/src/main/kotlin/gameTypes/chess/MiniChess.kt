package gameTypes.chess

import boards.Board2D
import utils.FenUtility

/**
 * Represents Mini Chess
 */
class MiniChess : StandardChess(FenUtility("rqkr/pppp/PPPP/RQKR w -", whiteStartingRow = 1, whitePromotionRow = 3, blackStartingRow = 2, blackPromotionRow = 0)) {
    override val board = Board2D(4, 4)
    override val name = "Mini Chess"
}