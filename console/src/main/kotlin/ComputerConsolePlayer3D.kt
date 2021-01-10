
import boards.Board3D
import coordinates.Coordinate3D
import gameTypes.GameType
import moveGenerators.MoveGenerator3D
import moves.Move3D
import pieces.Piece3D
import players.Player

class ComputerConsolePlayer3D(
    val delay: Long,
    override val gameType: GameType<Board3D, MoveGenerator3D, Piece3D, Coordinate3D>,
    override val player: Player
) : ConsolePlayer3D {
    override fun getTurn(choiceOfMoves: List<Move3D>): Move3D {
        println("Computer is thinking...")
        Thread.sleep(delay)
        return choiceOfMoves[(choiceOfMoves.indices).random()]
    }
}
