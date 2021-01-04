import gameTypes.chess.AbstractChess
import gameTypes.chess.CapablancaChess
import notationFormatter.ChessNotationInput

fun main() {
    println("Let's play chess!")
    val chess: AbstractChess = CapablancaChess()
    val player1 = HumanConsolePlayer(ChessNotationInput(), chess, chess.players[0])
    val player2 = HumanConsolePlayer(ChessNotationInput(), chess, chess.players[1])
//    val player2 = ComputerConsolePlayer(10, chess, chess.players[1])
//    chess.playerTurn = 1

    val game = ConsoleGameHelper(chess, player1, player2)
    game.start()
}
