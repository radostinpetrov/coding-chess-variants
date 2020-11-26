package gameTypes.xiangqi

import Coordinate
import boards.Board2D
import gameTypes.chess.AbstractChess
import gameTypes.xiangqi.rules.GeneralsRule
import moves.visitors.Board2DMoveVisitor
import pieces.xiangqi.* // ktlint-disable no-wildcard-imports

class Xiangqi : AbstractChess(listOf(GeneralsRule())) {
    override val board = Board2D(10, 9)
    override val moveVisitor by lazy { Board2DMoveVisitor(board) }

    override fun initGame() {
        val player1 = players[0]
        val player2 = players[1]
        for (i in 0..4) {
            board.addPiece(Coordinate(2 * i, 3), XiangqiRedSoldier(player1))
            board.addPiece(Coordinate(2 * i, 6), XiangqiBlueSoldier(player2))
        }
        board.addPiece(Coordinate(0, 0), XiangqiChariot(player1))
        board.addPiece(Coordinate(8, 0), XiangqiChariot(player1))
        board.addPiece(Coordinate(0, 9), XiangqiChariot(player2))
        board.addPiece(Coordinate(8, 9), XiangqiChariot(player2))
        board.addPiece(Coordinate(1, 2), XiangqiCannon(player1))
        board.addPiece(Coordinate(7, 2), XiangqiCannon(player1))
        board.addPiece(Coordinate(1, 7), XiangqiCannon(player2))
        board.addPiece(Coordinate(7, 7), XiangqiCannon(player2))
        board.addPiece(Coordinate(1, 0), XiangqiHorse(player1))
        board.addPiece(Coordinate(7, 0), XiangqiHorse(player1))
        board.addPiece(Coordinate(1, 9), XiangqiHorse(player2))
        board.addPiece(Coordinate(7, 9), XiangqiHorse(player2))
        board.addPiece(Coordinate(2, 0), XiangqiRedElephant(player1))
        board.addPiece(Coordinate(6, 0), XiangqiRedElephant(player1))
        board.addPiece(Coordinate(2, 9), XiangqiBlueElephant(player2))
        board.addPiece(Coordinate(6, 9), XiangqiBlueElephant(player2))
        board.addPiece(Coordinate(3, 0), XiangqiAdvisor(player1))
        board.addPiece(Coordinate(5, 0), XiangqiAdvisor(player1))
        board.addPiece(Coordinate(3, 9), XiangqiAdvisor(player2))
        board.addPiece(Coordinate(5, 9), XiangqiAdvisor(player2))
        board.addPiece(Coordinate(4, 0), XiangqiGeneral(player1))
        board.addPiece(Coordinate(4, 9), XiangqiGeneral(player2))
    }
}
