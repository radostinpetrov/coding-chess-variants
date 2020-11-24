package main.kotlin.gameTypes

import main.kotlin.boards.Board2D
import main.kotlin.GameMove
import main.kotlin.moves.visitors.MoveVisitor
// import History
import main.kotlin.players.Player

interface GameType {
    val board: Board2D
//    val history: MutableList<History>
    val players: MutableList<Player>
    var playerTurn: Int
    val moveVisitor: MoveVisitor<Board2D>
    var seed: Double?
    val NUM_PLAYERS: Int

    fun initGame()
    fun isOver(): Boolean
    // fun getWinner()
    fun getValidMoves(player: Player): List<GameMove>

    // fun getHistory(): List<Pair<Board, GameMove>>
    fun makeMove(gameMove: GameMove)
    fun addPlayer(player: Player) {
        players.add(player)
    }
//    fun turn() {
//        val player = players[playerTurn]
//        val moves = getValidMoves(player)
//        if (moves.isEmpty()) {
//            return
//        }
//
//        val move = player.getTurn(moves)
//
//        if (move != null) {
//            this.makeMove(move)
//            nextPlayer()
//        }
//    }
    fun nextPlayer() {
        playerTurn++
        playerTurn %= players.size
    }
    fun getCurrentPlayer(): Player {
        return players[playerTurn]
    }
    fun getNextPlayer(): Player {
        return players[(playerTurn + 1) % players.size]
    }
    fun checkValidGame(): Boolean {
        if (players.size != NUM_PLAYERS) {
            print("Incorrect number of players")
            return false
        }
        return true
    }
    fun playerMakeMove(move: GameMove) {

        // TODO discuss if we should keep this first check
        val validMoves = getValidMoves(getCurrentPlayer())

        if (!validMoves.contains(move)) {
            return
        }

        makeMove(move)
        nextPlayer()
    }
}
