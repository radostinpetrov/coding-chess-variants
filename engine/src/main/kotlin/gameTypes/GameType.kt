package gameTypes

import gameMoves.GameMove2D
import boards.Board2D
import players.Player

interface GameType {
    val board: Board2D
    val players: List<Player>
    var playerTurn: Int
    var seed: Double?
    val NUM_PLAYERS: Int
    val moveLog: MutableList<GameMove2D>

    fun initGame()
    fun isOver(): Boolean
    // fun getWinner()
    fun getValidMoves(player: Player): List<GameMove2D>
    fun makeMove(gameMove: GameMove2D)
    fun undoMove()
    fun inCheck(player: Player) : Boolean
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
    fun playerMakeMove(move: GameMove2D) {

        // TODO discuss if we should keep this first check
//        val validMoves = getValidMoves(getCurrentPlayer())
//
//        if (!validMoves.contains(move)) {
//            return
//        }

        makeMove(move)
        nextPlayer()
    }
}
