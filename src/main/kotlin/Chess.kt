import gameTypes.GrandChess
import gameTypes.StandardChess
import players.ComputerPlayer
import players.HumanPlayer

fun main(args: Array<String>) {
    println("Let's play chess!")

//    val chess = StandardChess()
    val chess = GrandChess()
    chess.addPlayer(ComputerPlayer())
    chess.addPlayer(ComputerPlayer())

    val game = Game(chess)
    game.start()
}
