package gameTypes.xiangqi

import coordinates.Coordinate2D
import boards.Board2D
import gameTypes.chess.AbstractChess
import endconditions.StandardEndConditions
import rules.GeneralsRule
import pieces.janggi.* // ktlint-disable no-wildcard-imports
import pieces.janggi.Elephant

class Janggi : AbstractChess(listOf(GeneralsRule()), listOf(StandardEndConditions())) {
    override val board = Board2D(10, 9)
    override val name = "Janggi"

    override fun initBoard() {
        val player1 = players[0]
        val player2 = players[1]
        for (i in 0..4) {
            board.addPiece(Coordinate2D(2 * i, 3), RedSoldier(player1))
            board.addPiece(Coordinate2D(2 * i, 6), BlueSoldier(player2))
        }
        board.addPiece(Coordinate2D(0, 0), Chariot(player1))
        board.addPiece(Coordinate2D(8, 0), Chariot(player1))
        board.addPiece(Coordinate2D(0, 9), Chariot(player2))
        board.addPiece(Coordinate2D(8, 9), Chariot(player2))
        board.addPiece(Coordinate2D(1, 2), Cannon(player1))
        board.addPiece(Coordinate2D(7, 2), Cannon(player1))
        board.addPiece(Coordinate2D(1, 7), Cannon(player2))
        board.addPiece(Coordinate2D(7, 7), Cannon(player2))
        board.addPiece(Coordinate2D(1, 0), Horse(player1))
        board.addPiece(Coordinate2D(7, 0), Horse(player1))
        board.addPiece(Coordinate2D(1, 9), Horse(player2))
        board.addPiece(Coordinate2D(7, 9), Horse(player2))
        board.addPiece(Coordinate2D(2, 0), Elephant(player1))
        board.addPiece(Coordinate2D(6, 0), Elephant(player1))
        board.addPiece(Coordinate2D(2, 9), Elephant(player2))
        board.addPiece(Coordinate2D(6, 9), Elephant(player2))
        board.addPiece(Coordinate2D(3, 0), Advisor(player1))
        board.addPiece(Coordinate2D(5, 0), Advisor(player1))
        board.addPiece(Coordinate2D(3, 9), Advisor(player2))
        board.addPiece(Coordinate2D(5, 9), Advisor(player2))
        board.addPiece(Coordinate2D(4, 1), General(player1))
        board.addPiece(Coordinate2D(4, 8), General(player2))
    }
}
