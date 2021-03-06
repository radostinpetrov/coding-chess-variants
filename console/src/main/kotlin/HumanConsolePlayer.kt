import boards.Board2D
import coordinates.Coordinate2D
import gameTypes.GameType
import moveGenerators.MoveGenerator2D
import moves.*
import pieces.Piece2D
import players.Player
import utils.notationFormatter.ChessNotationInput
import utils.notationFormatter.NotationFormatter

class HumanConsolePlayer2D(
    override val gameType: GameType<Board2D, MoveGenerator2D, Piece2D, Coordinate2D>,
    override val player: Player,
    val notationFormatter: NotationFormatter = ChessNotationInput(),
) : ConsolePlayer2D {

    fun isInteger(s: String?) = s?.toIntOrNull()?.let { true } ?: false
    override fun getTurn(choiceOfMoves: List<Move2D>): Move2D {
        var possibleMoves: List<Move2D> = mutableListOf()
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
                    is SimpleMove2D -> it.displayFrom == coordinate
                    is CompositeMove2D -> {
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
