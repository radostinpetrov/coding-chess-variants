package main.kotlin

import main.kotlin.players.ComputerPlayer
import main.kotlin.gameTypes.chess.AbstractChess
import main.kotlin.gameTypes.chess.StandardChess
import main.kotlin.players.ConsolePlayer

fun main() {
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

        gameTurn(game)
        game.display()
    }
}

fun gameTurn(game: ConsoleGameHelper) {
    val gt = game.gameType
    val player = gt.getCurrentPlayer()
    val moves = gt.getValidMoves(player)
    if (moves.isEmpty()) {
        return
    }

    // TODO fix
    val move = (player as ConsolePlayer).getTurn(moves)

    if (move != null) {
        gt.playerMakeMove(move)
    }
}
