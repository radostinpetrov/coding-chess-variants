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

    fun initGame()
    fun isOver(): Boolean
    // fun getWinner()
    fun getValidMoves(player: Player): List<GameMove>

    // fun getHistory(): List<Pair<Board, GameMove>>
    fun makeMove(gameMove: GameMove)
    fun addPlayer(player: Player)
    fun turn()
    fun nextPlayer()
    fun getCurrPlayer(): Player
    fun checkValidGame(): Boolean
}
