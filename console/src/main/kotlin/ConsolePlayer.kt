package players

import moves.Move2D
import gameTypes.GameType2D

// TODO fix this or something
abstract class ConsolePlayer(val gameType: GameType2D, val player: Player) {
    fun playTurn() {
        val moves = gameType.getValidMoves(player)
        if (moves.isEmpty()) {
            return
        }
        gameType.playerMakeMove(getTurn(moves))
    }

    abstract fun getTurn(choiceOfMoves: List<Move2D>): Move2D
}
