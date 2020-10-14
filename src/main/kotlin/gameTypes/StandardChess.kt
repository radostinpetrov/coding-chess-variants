package gameTypes

import Coordinate
import boards.Board
import GameMove
import History
import boards.Board2D
import pieces.Piece
import players.Player

class StandardChess() : GameType{

    override val board: Board<Piece> = Board2D(8, 8)
    override val history: MutableList<History> = mutableListOf()

    override val players: MutableList<Player> = ArrayList()
    override var playerTurn: Int = 0

    val NUM_PLAYERS = 2

    override fun initGame() {
        // board.addPiece()
        // board.addPiece()
        // board.addPiece()
        // board.addPiece()
        // board.addPiece()
        // board.addPiece()
        // board.addPiece()
        // board.addPiece()
        // board.addPiece()
    }

    override fun isOver(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getValidMoves(player: Player): List<GameMove> {
        val pieces = board.getPieces(player)
        val possibleMoves = mutableListOf<GameMove>()
        for (piece in pieces) {
            possibleMoves.addAll(getValidMoveForPiece(piece))
        }

        return possibleMoves
    }

    fun getValidMoveForPiece(pair: Pair<Piece, Coordinate>): List<GameMove> {

        val possibleMoves = mutableListOf<GameMove>()
        //validate possible moves


        return possibleMoves
    }


    // override fun getHistory(): List<Pair<Board, GameMove>> {
    //     TODO("Not yet implemented")
    // }

    override fun makeMove(gameMove: GameMove) {
        TODO("Not yet implemented")
    }


    override fun addPlayer(player: Player) {
        players.add(player)
    }

    override fun turn() {
        val player = players[playerTurn]

        val move = player.getTurn(getValidMoves(player))

        this.makeMove(move)

        nextPlayer()
    }

    override fun nextPlayer() {
        playerTurn++
        playerTurn %= players.size
    }


    override fun checkValidGame(): Boolean {
        if (players.size != NUM_PLAYERS) {
            print("incorrect number of players")
            return false
        }
        return true
    }


}