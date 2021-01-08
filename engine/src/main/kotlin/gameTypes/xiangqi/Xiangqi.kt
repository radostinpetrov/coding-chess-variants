package gameTypes.xiangqi

import boards.Board2D
import coordinates.Coordinate2D
import gameTypes.chess.AbstractChess
import winconditions.StandardWinConditions
import rules.GeneralsRule
import pieces.xiangqi.* // ktlint-disable no-wildcard-imports

class Xiangqi : AbstractChess(listOf(GeneralsRule()), listOf(StandardWinConditions())) {
    override val board = Board2D(10, 9)
    override val name = "Xiangqi"

    override fun initBoard() {
        val player1 = players[0]
        val player2 = players[1]
        for (i in 0..4) {
            board.addPiece(Coordinate2D(2 * i, 3), XiangqiRedSoldier(player1))
            board.addPiece(Coordinate2D(2 * i, 6), XiangqiBlueSoldier(player2))
        }
        board.addPiece(Coordinate2D(0, 0), XiangqiChariot(player1))
        board.addPiece(Coordinate2D(8, 0), XiangqiChariot(player1))
        board.addPiece(Coordinate2D(0, 9), XiangqiChariot(player2))
        board.addPiece(Coordinate2D(8, 9), XiangqiChariot(player2))
        board.addPiece(Coordinate2D(1, 2), XiangqiCannon(player1))
        board.addPiece(Coordinate2D(7, 2), XiangqiCannon(player1))
        board.addPiece(Coordinate2D(1, 7), XiangqiCannon(player2))
        board.addPiece(Coordinate2D(7, 7), XiangqiCannon(player2))
        board.addPiece(Coordinate2D(1, 0), XiangqiHorse(player1))
        board.addPiece(Coordinate2D(7, 0), XiangqiHorse(player1))
        board.addPiece(Coordinate2D(1, 9), XiangqiHorse(player2))
        board.addPiece(Coordinate2D(7, 9), XiangqiHorse(player2))
        board.addPiece(Coordinate2D(2, 0), XiangqiRedElephant(player1))
        board.addPiece(Coordinate2D(6, 0), XiangqiRedElephant(player1))
        board.addPiece(Coordinate2D(2, 9), XiangqiBlueElephant(player2))
        board.addPiece(Coordinate2D(6, 9), XiangqiBlueElephant(player2))
        board.addPiece(Coordinate2D(3, 0), XiangqiAdvisor(player1))
        board.addPiece(Coordinate2D(5, 0), XiangqiAdvisor(player1))
        board.addPiece(Coordinate2D(3, 9), XiangqiAdvisor(player2))
        board.addPiece(Coordinate2D(5, 9), XiangqiAdvisor(player2))
        board.addPiece(Coordinate2D(4, 0), XiangqiGeneral(player1))
        board.addPiece(Coordinate2D(4, 9), XiangqiGeneral(player2))
    }
}
