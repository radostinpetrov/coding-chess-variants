package main.kotlin.players

import main.kotlin.GameMove
import main.kotlin.notationFormatter.NotationFormatter

class TestHumanPlayer(val notationFormatter: NotationFormatter) : Player {
    fun isInteger(s: String?) = s?.toIntOrNull()?.let { true } ?: false

    override fun getTurn(choiceOfMoves: List<GameMove>): GameMove {
        var possibleMoves: List<GameMove> = mutableListOf()
        var input: String?
        while (possibleMoves.isEmpty()) {
            print("Enter the coordinate of the piece to move: ")
            input = readLine()
            if (input == null) {
                println("Empty Input, try again")
                continue
            }
            val coordinate = notationFormatter.strToCoordinate(input)
            if (coordinate == null) {
                println("Invalid coordinate, try again")
                continue
            }
            possibleMoves = choiceOfMoves.filter {
                when (it) {
                    is GameMove.BasicGameMove -> it.from == coordinate
                    is GameMove.CompositeGameMove -> {
                        true
                    }
                }
            }
            if (possibleMoves.isEmpty()) {
                println("No possible moves, try again")
            }
        }

        println("Select the move index from the following: ")
        for (i in possibleMoves.indices) {
            println((i + 1).toString() + ": " + notationFormatter.gameMoveToStr(possibleMoves[i]))
        }

        /* Ask user which move they want to make until they give a valid index. */
        var index = -1
        while (index < 1 || index > possibleMoves.size) {
            input = readLine()
            if (input != null && isInteger(input)) {
                index = input.toInt()
            } else {
                println("Please enter valid index")
            }
        }

        return possibleMoves[index - 1]
    }
}
