package main.kotlin

import main.kotlin.gameTypes.GameType
import main.kotlin.Observer
class Game(val gameType: GameType) {
    var turn = 0

    val observers = mutableListOf<Observer>()

    fun addObserver(observer: Observer) {
        observers.add(observer)
    }

    fun notifyObservers() {
        for (observer in observers) {
            observer.update()
        }
    }

    fun start() {
        if (!gameType.checkValidGame()) {
            return
        }

        gameType.initGame()
        this.display()
//        Thread.sleep(1000)

//        while (true) {
//            if (gameType.isOver()) {
//                break
//            }
//
//            gameType.turn()
//            this.notifyObservers()
////            Thread.sleep(100)
//        }
    }

    fun turn(): Boolean {
        if (gameType.isOver()) {
            return false
        }

        gameType.turn()
        return true
    }

    /* Display the board in terminal. */
    fun display() {
        val board = gameType.board
        val colour1 = "\u001B[31m"
        val colour2 = "\u001B[34m"
        val resetColour = "\u001B[0m"
        val player1 = gameType.players[0]
        val n = board.getBoardState().size

        for ((i, row) in board.getBoardState().withIndex()) {
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
