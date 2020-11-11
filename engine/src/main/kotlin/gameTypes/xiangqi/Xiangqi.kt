package main.kotlin.gameTypes.xiangqi

import main.kotlin.Coordinate
import main.kotlin.GameMove
import main.kotlin.boards.Board2D
import main.kotlin.gameTypes.chess.AbstractChess
import main.kotlin.moves.visitors.Board2DMoveVisitor
import main.kotlin.pieces.xiangqi.*
import main.kotlin.players.Player
import kotlin.math.max
import kotlin.math.min

class Xiangqi : AbstractChess() {
    override val board = Board2D(10, 9)
    override val moveVisitor by lazy { Board2DMoveVisitor(board) }

    override fun getValidMoves(player: Player): List<GameMove> {
        return super.getValidMoves(player).filter { !generalsFaceEachOther(it) }
    }

    private fun generalsFaceEachOther(move: GameMove): Boolean {
        super.makeMove(move)
        val generalCoordinates = board.getPieces().filter { it.first is XiangqiGeneral }
        val coordinate1 = generalCoordinates[0].second
        val coordinate2 = generalCoordinates[1].second

        if (coordinate1.x == coordinate2.x) {
            var blocked = false
            val x = coordinate1.x
            val start = min(coordinate1.y, coordinate2.y) + 1
            val end = max(coordinate1.y, coordinate2.y) - 1
            for (y in start..end) {
                if (board.getPiece(Coordinate(x, y)) != null) {
                    blocked = true
                }
            }
            undoMove()
            return !blocked
        }
        undoMove()
        return false
    }

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