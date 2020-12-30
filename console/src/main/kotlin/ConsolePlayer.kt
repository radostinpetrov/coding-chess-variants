package players

import gameMoves.GameMove2D
import gameTypes.GameType

// TODO fix this or something
abstract class ConsolePlayer(val gameType: GameType, val player: Player) {
    fun playTurn() {
        val moves = gameType.getValidMoves(player)
        if (moves.isEmpty()) {
            return
        }
        gameType.playerMakeMove(getTurn(moves))
    }

    abstract fun getTurn(choiceOfMoves: List<GameMove2D>): GameMove2D
}
