// TODO we will soon deprecate this file, any remaining logic should move to new console frontend

import coordinates.Coordinate2D
import gameMoves.GameMove2D
import gameTypes.GameType
import pieces.chess.*
import players.ConsolePlayer
import players.Player

class ConsoleGameHelper(val gameType: GameType, val player1: ConsolePlayer, val player2: ConsolePlayer) {
    var turn = 0

    fun start() {
        // TODO: Remove check valid game
//        if (!gameType.checkValidGame()) {
//            return
//        }

        gameType.initGame()

        val playerMapping = mapOf(gameType.players[0] to player1, gameType.players[1] to player2)
        val player1: Player = gameType.players[0]
        val player2: Player = gameType.players[1]
        this.display()
        val initMoves: List<GameMove2D.BasicGameMove> = listOf(
            GameMove2D.BasicGameMove(Coordinate2D(4, 1), Coordinate2D(4, 3), CapablancaWhitePawn(player1), player1),
            GameMove2D.BasicGameMove(Coordinate2D(0, 6), Coordinate2D(0, 5), CapablancaBlackPawn(player2), player2),
            GameMove2D.BasicGameMove(Coordinate2D(3, 0), Coordinate2D(7, 4), Bishop(player1), player1),
            GameMove2D.BasicGameMove(Coordinate2D(1, 6), Coordinate2D(1, 5), CapablancaBlackPawn(player2), player2),
            GameMove2D.BasicGameMove(Coordinate2D(4, 0), Coordinate2D(4, 2), Queen(player1), player1),
            GameMove2D.BasicGameMove(Coordinate2D(2, 6), Coordinate2D(2, 5), CapablancaBlackPawn(player2), player2),
            GameMove2D.BasicGameMove(Coordinate2D(2, 0), Coordinate2D(3, 2), Cardinal(player1), player1),
            GameMove2D.BasicGameMove(Coordinate2D(3, 6), Coordinate2D(3, 5), CapablancaBlackPawn(player2), player2),
            GameMove2D.BasicGameMove(Coordinate2D(1, 0), Coordinate2D(2, 2), Knight(player1), player1),
            GameMove2D.BasicGameMove(Coordinate2D(4, 6), Coordinate2D(4, 5), CapablancaBlackPawn(player2), player2),
        )
        for (move in initMoves) {
            gameType.makeMove(move)
        }
        this.display()
//        while (!gameType.isOver()) {
//            playerMapping[gameType.getCurrentPlayer()]!!.playTurn()
//            this.display()
//            Thread.sleep(100)
//        }
    }

    /* Display the board in terminal. */
    fun display() {
        val board = gameType.board
        // Player 1 is red and player 2 is blue
        val colour1 = "\u001B[31m"
        val colour2 = "\u001B[34m"
        val resetColour = "\u001B[0m"
        val player1 = gameType.players[0]
        val n = board.getBoardState().size

        for ((i, row) in board.getBoardState().reversed().withIndex()) {
            print("${n - i} ")
            for (piece in row) {
                if (piece != null) {
                    print((if (piece.player == player1) colour1 else colour2) + piece.getSymbol() + ' ' + resetColour)
                } else {
                    print("_ ")
                }
            }
            println()
        }
        print("  ")

        for (i in board.getBoardState()[0].indices) {
            print("${(i + 'a'.toInt()).toChar()} ")
        }
        println("\n----------------- turn: $turn")
        turn++
    }
}
