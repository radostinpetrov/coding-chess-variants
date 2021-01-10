import coordinates.Coordinate2D
import coordinates.Coordinate3D
import gameTypes.GameType2D
import gameTypes.checkers.Checkers
import gameTypes.chess.*
import gameTypes.chess3d.RaumschachChess
import gameTypes.xiangqi.Janggi
import gameTypes.xiangqi.Xiangqi
import utils.notationFormatter.ChessNotationInput

object Chess3D {

    @JvmStatic
    fun main(arg: Array<String>) {
        println("Let's play chess!")

        println("Select players:")
        println("1: Human vs Human")
        println("2: Human vs Computer")
        println("3: Computer vs Human")
        println("4: Computer vs Computer")

//        var input = readLine()
//        while (input == null) {
//            input = readLine()
//        }
//        val selection = input.toInt()

        val chess = RaumschachChess()
        chess.initGame()
        val boardState = chess.board.getBoardState()
        val board = chess.board
        val colour1 = "\u001B[31m"
        val colour2 = "\u001B[34m"
        val resetColour = "\u001B[0m"
        val player1 = chess.players[0]

        for (i in 0 until board.dims) {
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
            println()
            println()
        }

        val xd = chess.getValidMoves()
        print(xd)
//        val player1 = if (selection <= 2) {
//            HumanConsolePlayer(ChessNotationInput(), chess, chess.players[0])
//        } else {
//            ComputerConsolePlayer(10, chess, chess.players[1])
//        }
//
//        val player2 = if (selection % 2 == 1) {
//            HumanConsolePlayer(ChessNotationInput(), chess, chess.players[1])
//        } else {
//            ComputerConsolePlayer(10, chess, chess.players[1])
//        }
//
//        val game = ConsoleGameHelper(chess, player1, player2)
//        game.start()
    }
}