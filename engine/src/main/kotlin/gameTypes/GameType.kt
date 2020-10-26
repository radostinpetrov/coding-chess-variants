package gameTypes

import GameMove
import boards.Board2D
import moves.visitors.MoveVisitor
// import History
import players.Player

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
    fun checkValidGame(): Boolean
}
