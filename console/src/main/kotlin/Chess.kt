import gameTypes.GameType2D
import gameTypes.checkers.Checkers
import gameTypes.chess.*
import gameTypes.xiangqi.Janggi
import gameTypes.xiangqi.Xiangqi
import utils.notationFormatter.ChessNotationInput

object Chess {
    private val mappedVariants = mapOf(
        1 to StandardChess(),
        2 to GrandChess(),
        3 to CapablancaChess(),
        4 to Chess960(),
        5 to MiniChess(),
        6 to BalbosGame(),
        7 to AntiChess(),
        8 to Xiangqi(),
        9 to Janggi(),
        10 to Checkers()
    )

    @JvmStatic
    fun main(arg: Array<String>) {
        println("Let's play chess!")
        println("Input the index of the variant you want to play. ")

        for ((i, v) in mappedVariants) {
            println("$i: ${v.name}")
        }

        var input = readLine()
        while (input == null) {
            input = readLine()
        }

        val chess: GameType2D = mappedVariants[input.toInt()]!!

        println("Select players:")
        println("1: Human vs Human")
        println("2: Human vs Computer")
        println("3: Computer vs Human")
        println("4: Computer vs Computer")

        input = readLine()
        while (input == null) {
            input = readLine()
        }
        val selection = input.toInt()

        val player1 = if (selection <= 2) {
            HumanConsolePlayer(ChessNotationInput(), chess, chess.players[0])
        } else {
            ComputerConsolePlayer(10, chess, chess.players[1])
        }

        val player2 = if (selection % 2 == 1) {
            HumanConsolePlayer(ChessNotationInput(), chess, chess.players[1])
        } else {
            ComputerConsolePlayer(10, chess, chess.players[1])
        }

        val game = ConsoleGameHelper(chess, player1, player2)
        game.start()
    }
}