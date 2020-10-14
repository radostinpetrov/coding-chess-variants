package gameTypes

import Coordinate
import boards.Board
import GameMove
import History
import boards.Board2D
import pieces.Pawn
import pieces.Piece
import players.Player
import players.HumanPlayer

class StandardChess() : GameType{

    override val board: Board<Piece> = Board2D(8, 8)
    override val history: MutableList<History> = mutableListOf()

    override val players: MutableList<Player> = ArrayList()
    override var playerTurn: Int = 0

    val NUM_PLAYERS = 2

    override fun initGame() {
        val player1 = HumanPlayer()
        val player2 = HumanPlayer()
        for (i in 0..7){
            board.addPiece(Coordinate(i, 1), Pawn(player1))
            board.addPiece(Coordinate(i, 6), Pawn(player2))
        }
        // board.addPiece(Coordinate(0, 0), Rook(player1))
        // board.addPiece(Coordinate(7, 0), Rook(player1))
        // board.addPiece(Coordinate(0, 7), Rook(player2))
        // board.addPiece(Coordinate(7, 7), Rook(player2))
        // board.addPiece(Coordinate(1, 0), Knight(player1))
        // board.addPiece(Coordinate(6, 0), Knight(player1))
        // board.addPiece(Coordinate(1, 7), Knight(player2))
        // board.addPiece(Coordinate(6, 7), Knight(player2))
        // board.addPiece(Coordinate(2, 0), Bishop(player1))
        // board.addPiece(Coordinate(5, 0), Bishop(player1))
        // board.addPiece(Coordinate(2, 7), Bishop(player2))
        // board.addPiece(Coordinate(5, 7), Bishop(player2))
        // board.addPiece(Coordinate(4, 0), King(player1))
        // board.addPiece(Coordinate(4, 7), King(player2))
        // board.addPiece(Coordinate(3, 0), Queen(player1))
        // board.addPiece(Coordinate(3, 7), Queen(player2))
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