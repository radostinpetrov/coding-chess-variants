package playground

import boards.Board2D
import coordinates.Coordinate2D
import gameTypes.chess.AbstractChess2D
import moveGenerators.Direction
import moveGenerators.MoveGenerator2D
import pieces.Piece2D
import pieces.chess.Rook
import pieces.janggi.JanggiSoldier
import pieces.janggi.RedSoldier
import players.Player

class ChessPlayground : AbstractChess2D(endConditions = listOf()) {
    class PlaygroundPiece(override val player: Player) : Piece2D {
        override val moveGenerators: List<MoveGenerator2D>
            get() = listOf(
                MoveGenerator2D.Composite(listOf(MoveGenerator2D.Stepper(Direction.NORTH, 1, false), MoveGenerator2D.Stepper(listOf(Direction.NORTH_EAST, Direction.NORTH_WEST), 1, true))),
                MoveGenerator2D.Composite(listOf(MoveGenerator2D.Stepper(Direction.EAST, 1, false), MoveGenerator2D.Stepper(listOf(Direction.NORTH_EAST, Direction.SOUTH_EAST), 1, true))),
                MoveGenerator2D.Composite(listOf(MoveGenerator2D.Stepper(Direction.SOUTH, 1, false), MoveGenerator2D.Stepper(listOf(Direction.SOUTH_WEST, Direction.SOUTH_EAST), 1, true))),
                MoveGenerator2D.Composite(listOf(MoveGenerator2D.Stepper(Direction.WEST, 1, false), MoveGenerator2D.Stepper(listOf(Direction.SOUTH_WEST, Direction.NORTH_WEST), 1, true))),
            )

        override fun getSymbol(): String = "D"
    }
    override val board: Board2D = Board2D(10, 9)
    override val name = "Chess Playground"

    override fun initBoard() {
        board.addPiece(Coordinate2D(5, 2), PlaygroundPiece(players[0]))
        board.addPiece(Coordinate2D(6, 2), RedSoldier(players[0]))
        board.addPiece(Coordinate2D(3, 2), RedSoldier(players[0]))
    }
}
