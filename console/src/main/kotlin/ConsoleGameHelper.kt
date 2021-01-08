import boards.Board2D
import coordinates.Coordinate2D
import gameTypes.GameType2D

class ConsoleGameHelper(val gameType: GameType2D, val player1: ConsolePlayer, val player2: ConsolePlayer) {
    var turn = 0

    fun start() {
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
        val board: Board2D = gameType.board
        // Player 1 is red and player 2 is blue
        val colour1 = "\u001B[31m"
        val colour2 = "\u001B[34m"
        val resetColour = "\u001B[0m"
        val player1 = gameType.players[0]

        val boardState = board.getBoardState()

        for (i in (board.rows - 1) downTo 0) {
            print("$i ")
            for (j in 0 until board.cols) {
                val coordinate = Coordinate2D(j, i)
                if (!board.isInBounds(coordinate)) {
                    print("  ")
                    continue
                }
                val piece = boardState[coordinate]
                if (piece != null) {
                    print((if (piece.player == player1) colour1 else colour2) + piece.getSymbol() + ' ' + resetColour)
                } else {
                    print("_ ")
                }
            }
            println()
        }
        print("  ")

        for (i in 0 until board.cols) {
            print("${(i + 'a'.toInt()).toChar()} ")
        }
        println("\n----------------- turn: $turn")
        turn++
    }
}
