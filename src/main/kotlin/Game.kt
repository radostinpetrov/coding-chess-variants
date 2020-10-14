import boards.Board
import gameTypes.GameType
import pieces.Piece


class Game(val gameType: GameType) {
    var turn = 0

    fun start() {

        if (!gameType.checkValidGame()) {
            return
        }

        gameType.initGame()

//        while (true) {
//
//            if (gameType.isOver()) {
//                break
//            }
//
//            gameType.turn()
//            this.display()
//        }

        Thread.sleep(1000)
        this.display()
        Thread.sleep(1000)
        this.display()

    }

    fun display() {
        val board = gameType.board


        for (row in board.getBoardState()) {
            for (piece in row) {
                if (piece != null) {
                    print(piece.getSymbol() + ' ')
                } else {
                    print("_ ")
                }
            }
            println()
        }

        println("--------------- turn: $turn")
        turn++

    }
}
