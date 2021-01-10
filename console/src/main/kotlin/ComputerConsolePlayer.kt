import moves.Move
import gameTypes.GameType2D
import players.Player

class ComputerConsolePlayer(val delay: Long, gameType: GameType2D, player: Player) : ConsolePlayer(gameType, player) {
    override fun getTurn(choiceOfMoves: List<Move>): Move {
        println("Computer is thinking...")
        Thread.sleep(delay)
        return choiceOfMoves[(choiceOfMoves.indices).random()]
    }
}
