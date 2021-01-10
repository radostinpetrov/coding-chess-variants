
import boards.Board2D
import boards.Board3D
import coordinates.Coordinate2D
import coordinates.Coordinate3D

class ConsoleGameHelper(val gameType: Game, val player1: ConsolePlayer<*, *, *, *>, val player2: ConsolePlayer<*, *, *, *>) {
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
        if (gameType.board is Board2D) {
            display2D(gameType.board as Board2D)
        } else if (gameType.board is Board3D) {
            display3D(gameType.board as Board3D)
        }
    }

    fun display2D(board: Board2D) {
        // Player 1 is red and player 2 is blue
        val colour1 = "\u001B[31m"
        val colour2 = "\u001B[34m"
        val resetColour = "\u001B[0m"
        val player1 = gameType.players[0]

        val boardState = board.getBoardState()

        for (i in (board.rows - 1) downTo 0) {
            print("${i + 1} ")
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

    fun display3D(board: Board3D) {
        val colour1 = "\u001B[31m"
        val colour2 = "\u001B[34m"
        val resetColour = "\u001B[0m"
        val player1 = gameType.players[0]

        val boardState = board.getBoardState()

        for (i in 0 until board.dims) {
            print("--- BOARD ${('A'.toInt() + i).toChar()} ---\n")
            for (j in (board.rows - 1) downTo 0) {
                print("${j + 1} ")
                for (k in 0 until board.cols) {
                    val coordinate = Coordinate3D(k, j, i)
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

            for (j in 0 until board.cols) {
                print("${(j + 'a'.toInt()).toChar()} ")
            }
            println()
            println()
        }
        println("\n----------------- turn: $turn")
        turn++
    }
}
