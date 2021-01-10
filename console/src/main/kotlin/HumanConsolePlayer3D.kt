import boards.Board3D
import coordinates.Coordinate3D
import gameTypes.GameType
import moveGenerators.MoveGenerator3D
import moves.*
import pieces.Piece3D
import players.Player
import utils.notationFormatter.ChessNotationInput3D
import utils.notationFormatter.NotationFormatter

class HumanConsolePlayer3D(
    override val gameType: GameType<Board3D, MoveGenerator3D, Piece3D, Coordinate3D>,
    override val player: Player
) : ConsolePlayer3D {
    val notationFormatter = ChessNotationInput3D()

    fun isInteger(s: String?) = s?.toIntOrNull()?.let { true } ?: false
    override fun getTurn(choiceOfMoves: List<Move3D>): Move3D {
        var possibleMoves: List<Move3D> = mutableListOf()
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
                    is SimpleMove3D -> it.displayFrom == coordinate
                    is CompositeMove3D -> {
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
            println((i + 1).toString() + ": " + notationFormatter.moveToStr(possibleMoves[i]))
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
