
import boards.Board2D
import coordinates.Coordinate2D
import gameTypes.GameType
import moveGenerators.MoveGenerator2D
import moves.Move2D
import pieces.Piece2D
import players.Player

class ComputerConsolePlayer2D(
    val delay: Long,
    override val gameType: GameType<Board2D, MoveGenerator2D, Piece2D, Coordinate2D>,
    override val player: Player
) : ConsolePlayer2D {
    override fun getTurn(choiceOfMoves: List<Move2D>): Move2D {
        println("Computer is thinking...")
        Thread.sleep(delay)
        return choiceOfMoves[(choiceOfMoves.indices).random()]
    }
}
