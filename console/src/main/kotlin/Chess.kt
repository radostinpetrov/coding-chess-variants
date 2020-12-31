import gameTypes.chess.AbstractChess
import gameTypes.chess.CapablancaChess
import gameTypes.chess.StandardChess
import notationFormatter.ChessNotationInput
import players.ComputerConsolePlayer

fun main() {
    println("Let's play chess!")
    val chess: AbstractChess = CapablancaChess()
    val player1 = HumanConsolePlayer(ChessNotationInput(), chess, chess.players[0])
    val player2 = ComputerConsolePlayer(10, chess, chess.players[1])
//    chess.playerTurn = 1

    val game = ConsoleGameHelper(chess, player1, player2)
    game.start()
}
