package main.kotlin

import main.kotlin.gameTypes.chess.StandardChess
import main.kotlin.players.ComputerPlayer
import players.HumanPlayer

fun main(args: Array<String>) {
    println("Let's play chess!")
    val chess = StandardChess()

//    chess.addPlayer(HumanPlayer())
//    chess.addPlayer(ComputerPlayer(10))
    chess.addPlayer(HumanPlayer(ChessNotationInput(chess.board.n)))
    chess.addPlayer(HumanPlayer(ChessNotationInput(chess.board.n)))

    val game = Game(chess)
    game.start()
}
