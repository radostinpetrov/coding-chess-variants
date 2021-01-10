package playground

import boards.Board2D
import coordinates.Coordinate2D
import gameTypes.chess.AbstractChess2D
import moveGenerators.MoveGenerator2D
import pieces.Piece2D
import players.Player

class ChessPlayground : AbstractChess2D(endConditions = listOf()) {
    class PlaygroundPiece(override val player: Player) : Piece2D {
        override val moveGenerators: List<MoveGenerator2D>
            get() = listOf(MoveGenerator2D.Slider(D = true, A = true))

        override fun getSymbol(): String = "D"
    }
    override val board: Board2D = Board2D(8, 8)
    override val name = "Chess Playground"

    override fun initBoard() {
        board.addPiece(Coordinate2D(4, 3), PlaygroundPiece(players[0]))
    }
}
