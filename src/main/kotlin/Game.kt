import boards.Board
import gameTypes.GameType
import players.Player

class Game(val gameType: GameType, val board: Board) {
    var players: MutableList<Player> = ArrayList()

    var playerTurn: Int = 0

    fun addPlayer(player: Player) {
        players.add(player)
    }

    fun turn(player: Player) {

        var move = player.getTurn()

        gameType.makeMove(move)
    }

    fun nextPlayer() {
        playerTurn++
        playerTurn %= players.size
    }


    fun start() {
        if (players.size < 2) {
            print("not enough players")
            return
        }

        while (true) {

            if (gameType.isOver()) {
                break
            }

            turn(players[playerTurn])

            nextPlayer()
        }

        //gameType.getWinner()
    }

}
