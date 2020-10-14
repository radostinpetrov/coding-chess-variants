import boards.Board
import gameTypes.GameType
import pieces.Piece


class Game(val gameType: GameType) {
    fun start() {

        if (!gameType.checkValidGame()) {
            return
        }

        while (true) {

            if (gameType.isOver()) {
                break
            }

            gameType.turn()
            this.display()
        }
    }

    fun display() {
        val board = gameType.board

        for (row in board.getBoardState()) {
            for (piece in row) {
                if (piece != null) {
                    print('P')
                } else {
                    print('!')
                }
            }
            println()
        }

    }
}
