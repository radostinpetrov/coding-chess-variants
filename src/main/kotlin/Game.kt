import boards.Board
import gameTypes.GameType
import pieces.Pawn
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
        gameType.makeMove(GameMove(Coordinate(1, 1), Coordinate(1, 3), gameType.board.getPiece(Coordinate(1,1))!!))
        this.display()
        Thread.sleep(1000)
        gameType.makeMove(GameMove(Coordinate(0, 6), Coordinate(0, 5), gameType.board.getPiece(Coordinate(0,6))!!))
        this.display()
        Thread.sleep(1000)
        gameType.makeMove(GameMove(Coordinate(4, 0), Coordinate(4, 7), gameType.board.getPiece(Coordinate(4,0))!!, gameType.board.getPiece(Coordinate(4, 7))))
        this.display()
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
