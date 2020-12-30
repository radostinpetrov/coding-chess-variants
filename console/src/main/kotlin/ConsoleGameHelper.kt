// TODO we will soon deprecate this file, any remaining logic should move to new console frontend

import gameTypes.GameType
import players.ConsolePlayer

class ConsoleGameHelper(val gameType: GameType, val player1: ConsolePlayer, val player2: ConsolePlayer) {
    var turn = 0

    fun start() {
        // TODO: Remove check valid game
//        if (!gameType.checkValidGame()) {
//            return
//        }

        gameType.initGame()

        val playerMapping = mapOf(gameType.players[0] to player1, gameType.players[1] to player2)
        this.display()

        while (!gameType.isOver()) {
            playerMapping[gameType.getCurrentPlayer()]!!.playTurn()
            this.display()
            Thread.sleep(100)
        }
    }

    /* Display the board in terminal. */
    fun display() {
        val board = gameType.board
        val colour1 = "\u001B[31m"
        val colour2 = "\u001B[34m"
        val resetColour = "\u001B[0m"
        val player1 = gameType.players[0]
        val n = board.getBoardState().size

        for ((i, row) in board.getBoardState().withIndex()) {
            print("${n - i} ")
            for (piece in row) {
                if (piece != null) {
                    print((if (piece.player == player1) colour1 else colour2) + piece.getSymbol() + ' ' + resetColour)
                } else {
                    print("_ ")
                }
            }
            println()
        }
        print("  ")

        for (i in board.getBoardState()[0].indices) {
            print("${(i + 'a'.toInt()).toChar()} ")
        }
        println("\n----------------- turn: $turn")
        turn++
    }
}
