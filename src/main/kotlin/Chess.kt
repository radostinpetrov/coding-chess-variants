import gameTypes.GrandChess
import players.ComputerPlayer

fun main(args: Array<String>) {
    println("Let's play chess!")
    val chess = GrandChess()

    chess.addPlayer(ComputerPlayer(10))
    chess.addPlayer(ComputerPlayer(10))
//    chess.addPlayer(HumanPlayer(ChessNotationInput(chess.board.n)))
//    chess.addPlayer(HumanPlayer(ChessNotationInput(chess.board.n)))

    val game = Game(chess)
    game.start()
}
