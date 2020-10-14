import gameTypes.StandardChess
import players.HumanPlayer

fun main(args: Array<String>) {
    println("Let's play chess!")
    val chess = StandardChess()
    chess.addPlayer(HumanPlayer())
    chess.addPlayer(HumanPlayer())

    val game = Game(chess)
    game.start()
}
