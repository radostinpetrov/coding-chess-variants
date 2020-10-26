package gameTypes.chess

import Coordinate
import GameMove
import boards.Board2D
import gameTypes.GameType
import moves.visitors.Board2DMoveVisitor
import moves.visitors.MoveVisitor
import pieces.*
import players.Player

abstract class AbstractChess : GameType {

    override val players: MutableList<Player> = ArrayList()
    override var playerTurn: Int = 1
    override val moveVisitor by lazy { Board2DMoveVisitor(board) }
    val moveLog: MutableList<GameMove> = mutableListOf()
    var stalemate = false
    var checkmate = false

    val NUM_PLAYERS = 2

    override fun isOver(): Boolean {
        return checkmate || stalemate
    }

    override fun getValidMoves(player: Player): List<GameMove> {
        val possibleMoves = getPossibleMoves(player)
        val moves = filterForCheck(player, possibleMoves)
        if (moves.isEmpty()) {
            if (inCheck(player)) {
                checkmate = true
            } else {
                stalemate = true
            }
        } else {
            checkmate = false
            stalemate = false
        }
        return moves
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
        for (move in possibleMoves) {
            makeMove(move)
            if (!inCheck(player)) {
                res.add(move)
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
        val nextPlayer = players[(playerTurn + 1) % 2]
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
        // validate possible moves

        val piece = pair.first
        val coordinate = pair.second

        for (move in piece.moveTypes) {
            possibleMoves.addAll(moveVisitor.visit(coordinate, piece, move))
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
        val moves = getValidMoves(player)
        if (moves.isEmpty()) {
            return
        }

        val move = player.getTurn(moves)

        this.makeMove(move)

        nextPlayer()
    }

    override fun nextPlayer() {
        playerTurn++
        playerTurn %= players.size
    }

    override fun checkValidGame(): Boolean {
        if (players.size != NUM_PLAYERS) {
            print("Incorrect number of players")
            return false
        }
        return true
    }
}