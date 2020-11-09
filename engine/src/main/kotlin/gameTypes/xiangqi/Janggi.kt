import main.kotlin.Coordinate
import main.kotlin.boards.Board2D
import main.kotlin.gameTypes.chess.AbstractChess
import main.kotlin.moves.visitors.Board2DMoveVisitor
import main.kotlin.pieces.xiangqi.*

class Janggi : AbstractChess(listOf()) {
    override val board = Board2D(10, 9)
    override val moveVisitor by lazy { Board2DMoveVisitor(board) }

    override fun initGame() {
        val player1 = players[0]
        val player2 = players[1]
        for (i in 0..4) {
            board.addPiece(Coordinate(2 * i, 3), RedSoldier(player1))
            board.addPiece(Coordinate(2 * i, 6), BlueSoldier(player2))
        }
        board.addPiece(Coordinate(0, 0), Chariot(player1))
        board.addPiece(Coordinate(8, 0), Chariot(player1))
        board.addPiece(Coordinate(0, 9), Chariot(player2))
        board.addPiece(Coordinate(8, 9), Chariot(player2))
        board.addPiece(Coordinate(1, 2), Cannon(player1))
        board.addPiece(Coordinate(7, 2), Cannon(player1))
        board.addPiece(Coordinate(1, 7), Cannon(player2))
        board.addPiece(Coordinate(7, 7), Cannon(player2))
        board.addPiece(Coordinate(1, 0), Horse(player1))
        board.addPiece(Coordinate(7, 0), Horse(player1))
        board.addPiece(Coordinate(1, 9), Horse(player2))
        board.addPiece(Coordinate(7, 9), Horse(player2))
        board.addPiece(Coordinate(2, 0), Elephant(player1))
        board.addPiece(Coordinate(6, 0), Elephant(player1))
        board.addPiece(Coordinate(2, 9), Elephant(player2))
        board.addPiece(Coordinate(6, 9), Elephant(player2))
        board.addPiece(Coordinate(3, 0), Advisor(player1))
        board.addPiece(Coordinate(5, 0), Advisor(player1))
        board.addPiece(Coordinate(3, 9), Advisor(player2))
        board.addPiece(Coordinate(5, 9), Advisor(player2))
        board.addPiece(Coordinate(4, 1), General(player1))
        board.addPiece(Coordinate(4, 8), General(player2))
    }
}