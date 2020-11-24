package main.kotlin

import main.kotlin.gameTypes.chess.AbstractChess
import main.kotlin.gameTypes.chess.StandardChess
import main.kotlin.players.ComputerPlayer

fun main(args: Array<String>) {
    println("Let's play chess!")
    val chess: AbstractChess = StandardChess()
    chess.playerTurn = 1

//    chess.addPlayer(HumanPlayer())
    chess.addPlayer(ComputerPlayer(10))
    chess.addPlayer(ComputerPlayer(10))
//    chess.addPlayer(TestHumanPlayer(ChessNotationInput(chess.board.n)))
//    chess.addPlayer(TestHumanPlayer(ChessNotationInput(chess.board.n)))

    val game = ConsoleGameHelper(chess)
    game.start()

    while (true) {
        if (game.gameType.isOver()) {
            break
        }

        game.gameType.turn()
        game.display()
    }
}
