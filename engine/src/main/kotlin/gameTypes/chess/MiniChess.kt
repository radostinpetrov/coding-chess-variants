package gameTypes.chess

import boards.Board2D
import gameTypes.FenUtility

class MiniChess() : StandardChess(FenUtility("rqkr/pppp/PPPP/RQKR w -")) {
    override val board = Board2D(4, 4)
}