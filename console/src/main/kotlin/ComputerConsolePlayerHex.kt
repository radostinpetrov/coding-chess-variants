
import boards.BoardHex
import coordinates.Coordinate2D
import gameTypes.GameType
import moveGenerators.MoveGeneratorHex
import moves.MoveHex
import pieces.hex.PieceHex
import players.Player

class ComputerConsolePlayerHex(
    val delay: Long,
    override val gameType: GameType<BoardHex, MoveGeneratorHex, PieceHex, Coordinate2D>,
    override val player: Player
) : ConsolePlayerHex {
    override fun getTurn(choiceOfMoves: List<MoveHex>): MoveHex {
        println("Computer is thinking...")
        Thread.sleep(delay)
        return choiceOfMoves[(choiceOfMoves.indices).random()]
    }
}
