package players

import gameMoves.GameMove2D
import gameTypes.GameType

class ComputerConsolePlayer(val delay: Long, gameType: GameType, player: Player) : ConsolePlayer(gameType, player) {
    override fun getTurn(choiceOfMoves: List<GameMove2D>): GameMove2D {
        println("Computer is thinking...")
        Thread.sleep(0)
        return choiceOfMoves[(choiceOfMoves.indices).random()]
    }
}
