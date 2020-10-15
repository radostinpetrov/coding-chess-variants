package players

import Coordinate
import GameMove

class HumanPlayer: Player {
    override fun getTurn(choiceOfMoves: List<GameMove>): GameMove {
        fun isInteger(s: String?) = s?.toIntOrNull()?.let { true } ?: false

        var possibleMoves: List<GameMove> = mutableListOf()
        var input: String?

        print("Enter the coordinate of the piece to move: ")

        while (possibleMoves.isEmpty()) {
            // number number e.g 0 1
            input = readLine()
            while (input == null || input.split(" ").size < 2) {
                input = readLine()
            }

            val (x, y) = input.split(" ")

            if (isInteger(x) || isInteger(y)) {
                val pieceCoordinate = Coordinate(x.toInt(), y.toInt())

                possibleMoves = choiceOfMoves.filter { m -> m.from == pieceCoordinate}

                if (possibleMoves.isEmpty()) {
                    print("No possible moves. Try again: ")
                }
            }
        }

        println("Select a move from the following: ")
        for (i in possibleMoves.indices) {
            println((i + 1).toString() + ": " + possibleMoves[i])
        }

        var index = -1
        while (index <= 0) {
            input = null
            while (input == null) {
                input = readLine()
            }

            if (isInteger(input)) {
                index = input.toInt()
                if (1 > index || possibleMoves.size < index) {
                    index = -1
                }
            }
        }

        return possibleMoves[index - 1]
    }
}