import gameTypes.GameType


class Game(val gameType: GameType) {
    var turn = 0

    fun start() {

        if (!gameType.checkValidGame()) {
            return
        }

        gameType.initGame()

        while (true) {

            if (gameType.isOver()) {
                break
            }

            gameType.turn()
            this.display()
            Thread.sleep(5)
        }
    }

    fun display() {
        val board = gameType.board
        val colour1 = "\u001B[31m"
        val colour2 = "\u001B[34m"
        val resetColour = "\u001B[0m"
        val player1 = gameType.players[0]

        for (row in board.getBoardState()) {
            for (piece in row) {
                if (piece != null) {
                    print((if (piece.player == player1)  colour1 else colour2 ) + piece.getSymbol() + ' ' + resetColour)
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
