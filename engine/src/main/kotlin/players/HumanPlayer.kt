package players

import GameMove
import NotationFormatter

abstract class HumanPlayer : Player {
    fun isInteger(s: String?) = s?.toIntOrNull()?.let { true } ?: false

    abstract override fun getTurn(choiceOfMoves: List<GameMove>): GameMove

    // val notationFormatter: NotationFormatter
    // override fun getTurn(choiceOfMoves: List<GameMove>): GameMove {
    //     var possibleMoves: List<GameMove> = mutableListOf()
    //     var input: String?
    //     while (possibleMoves.isEmpty()) {
    //         print("Enter the coordinate of the piece to move: ")
    //         input = readLine()
    //         if (input == null) {
    //             println("Empty Input, try again")
    //             continue
    //         }
    //         val coordinate = notationFormatter.strToCoordinate(input)
    //         if (coordinate == null) {
    //             println("Invalid coordinate, try again")
    //             continue
    //         }
    //         possibleMoves = choiceOfMoves.filter { m -> m.from == coordinate }
    //         if (possibleMoves.isEmpty()) {
    //             println("No possible moves, try again")
    //         }
    //     }
    //
    //     println("Select the move index from the following: ")
    //     for (i in possibleMoves.indices) {
    //         println((i + 1).toString() + ": " + notationFormatter.gameMoveToStr(possibleMoves[i]))
    //     }
    //
    //     /* Ask user which move they want to make until they give a valid index. */
    //     var index = -1
    //     while (index < 1 || index > possibleMoves.size) {
    //         input = readLine()
    //         if (input != null && isInteger(input)) {
    //             index = input.toInt()
    //         } else {
    //             println("Please enter valid index")
    //         }
    //     }
    //
    //     return possibleMoves[index - 1]
    // }
}
