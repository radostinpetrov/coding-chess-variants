import main.kotlin.Coordinate
import main.kotlin.GameMove
import main.kotlin.boards.Board2D
import main.kotlin.gameTypes.chess.AbstractChess
import main.kotlin.moves.visitors.Board2DMoveVisitor
import main.kotlin.pieces.janggi.*
import main.kotlin.players.Player
import kotlin.math.max
import kotlin.math.min

class Janggi : AbstractChess() {
    override val board = Board2D(10, 9)
    override val moveVisitor by lazy { Board2DMoveVisitor(board) }

    override fun getValidMoves(player: Player): List<GameMove> {
        return super.getValidMoves(player)
                .distinct()
                .filter { !generalsFaceEachOther(it) }
    }

    private fun generalsFaceEachOther(move: GameMove): Boolean {
        super.makeMove(move)
        val generalCoordinates = board.getPieces().filter { it.first is General }
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