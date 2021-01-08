import moves.Move2D
import gameTypes.GameType2D
import players.Player

class ComputerConsolePlayer(val delay: Long, gameType: GameType2D, player: Player) : ConsolePlayer(gameType, player) {
    override fun getTurn(choiceOfMoves: List<Move2D>): Move2D {
        println("Computer is thinking...")
        Thread.sleep(0)
        return choiceOfMoves[(choiceOfMoves.indices).random()]
    }
}
