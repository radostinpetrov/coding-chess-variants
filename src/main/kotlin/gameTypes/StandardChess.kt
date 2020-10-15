package gameTypes

import Coordinate
import GameMove
//import History
import boards.Board2D
import moves.Direction
import moves.Move
import moves.visitors.MoveVisitor
import pieces.*
import players.Player
import players.HumanPlayer

open class StandardChess() : GameType{

    override val board = Board2D(8, 8)
//    override val history: MutableList<History> = mutableListOf()

    override val players: MutableList<Player> = ArrayList()
    override var playerTurn: Int = 1
    val moveVisitor by lazy { MoveVisitor(board) }
    val moveLog: MutableList<GameMove> = mutableListOf()

    val NUM_PLAYERS = 2

    override fun initGame() {
        val player1 = players[0]
        val player2 = players[1]
        for (i in 0..7){
            board.addPiece(Coordinate(i, 1), WhitePawn(player1))
            board.addPiece(Coordinate(i, 6), BlackPawn(player2))
        }
         board.addPiece(Coordinate(0, 0), Rook(player1))
         board.addPiece(Coordinate(7, 0), Rook(player1))
         board.addPiece(Coordinate(0, 7), Rook(player2))
         board.addPiece(Coordinate(7, 7), Rook(player2))
         board.addPiece(Coordinate(1, 0), Knight(player1))
         board.addPiece(Coordinate(6, 0), Knight(player1))
         board.addPiece(Coordinate(1, 7), Knight(player2))
         board.addPiece(Coordinate(6, 7), Knight(player2))
         board.addPiece(Coordinate(2, 0), Bishop(player1))
         board.addPiece(Coordinate(5, 0), Bishop(player1))
         board.addPiece(Coordinate(2, 7), Bishop(player2))
         board.addPiece(Coordinate(5, 7), Bishop(player2))
         board.addPiece(Coordinate(4, 0), King(player1))
         board.addPiece(Coordinate(4, 7), King(player2))
         board.addPiece(Coordinate(3, 0), Queen(player1))
         board.addPiece(Coordinate(3, 7), Queen(player2))
    }

    override fun isOver(): Boolean {
        return false
    }

    override fun getValidMoves(player: Player): List<GameMove> {
        val possibleMoves = getPossibleMoves(player)

        return filterForCheck(player, possibleMoves)
    }

    fun getPossibleMoves(player: Player): List<GameMove> {
        val pieces = board.getPieces(player)
        val possibleMoves = mutableListOf<GameMove>()
        for (piece in pieces) {
            possibleMoves.addAll(getValidMoveForPiece(piece))
        }
        return possibleMoves
    }

    fun filterForCheck(player: Player, possibleMoves: List<GameMove>): List<GameMove> {
        val res = mutableListOf<GameMove>()
        for (i in 0..possibleMoves.size-1){
            makeMove(possibleMoves[i])
            if (!inCheck(player)) {
                res.add(possibleMoves[i])
            }
            undoMove()
        }
        return res
    }

    fun inCheck(player: Player): Boolean {
        val kingCoordinate = board.getPieces(player).find { p -> p.first.player == player && p.first is King }!!.second
        return squareUnderAttack(kingCoordinate)
    }

    fun squareUnderAttack(coordinate: Coordinate): Boolean {
        val nextPlayer = players[(playerTurn+1)%2]
        val moves = getPossibleMoves(nextPlayer)
        for (m in moves) {
            if (m.to.x == coordinate.x && m.to.y == coordinate.y) {
                return true
            }
        }
        return false
    }

    fun undoMove() {
        if (moveLog.size == 0) {
            return
        }
        val gameMove = moveLog.removeAt(moveLog.size - 1)
        if (gameMove.piecePromotedTo != null) {
            board.removePiece(gameMove.to, gameMove.piecePromotedTo)
        } else {
            board.removePiece(gameMove.to, gameMove.pieceMoved)
        }

        if (gameMove.pieceCaptured != null) {
            // CHECK FOR BUG HERE
            board.addPiece(gameMove.to, gameMove.pieceCaptured)
        }
        board.addPiece(gameMove.from, gameMove.pieceMoved)
    }

    fun getValidMoveForPiece(pair: Pair<Piece, Coordinate>): List<GameMove> {

        val possibleMoves = mutableListOf<GameMove>()
        //validate possible moves

        val piece = pair.first
        val coordinate = pair.second

        for (move in piece.moveTypes) {
            possibleMoves.addAll(moveVisitor.getValidMoveHelper(coordinate, piece, move))
        }

        return possibleMoves
    }

    // override fun getHistory(): List<Pair<Board, GameMove>> {
    //     TODO("Not yet implemented")
    // }

    override fun makeMove(gameMove: GameMove) {
        board.removePiece(gameMove.from, gameMove.pieceMoved)
        if (gameMove.pieceCaptured != null) {
            board.removePiece(board.getPieceCoordinate(gameMove.pieceCaptured)!!, gameMove.pieceCaptured)
        }
        if (gameMove.piecePromotedTo != null) {
            board.addPiece(gameMove.to, gameMove.piecePromotedTo)
        } else {
            board.addPiece(gameMove.to, gameMove.pieceMoved)
        }
        moveLog.add(gameMove)
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