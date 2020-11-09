package main.kotlin.gameTypes.chess

import main.kotlin.Coordinate
import main.kotlin.GameMove
import main.kotlin.gameTypes.GameType
import main.kotlin.gameTypes.chess.rules.SpecialRules
import main.kotlin.moves.visitors.Board2DMoveVisitor
import main.kotlin.pieces.King
import main.kotlin.pieces.Piece
import main.kotlin.players.Player

abstract class AbstractChess(val rules: List<SpecialRules<AbstractChess>> = listOf()) : GameType {

    override val players: MutableList<Player> = ArrayList()
    override var playerTurn: Int = 0

    override val moveVisitor by lazy { Board2DMoveVisitor(board) }
    val moveLog: MutableList<GameMove> = mutableListOf()
    var stalemate = false
    var checkmate = false

    val NUM_PLAYERS = 2

    override fun isOver(): Boolean {
        return checkmate || stalemate
    }

    override fun getValidMoves(player: Player): List<GameMove> {
        val possibleMoves = getPossibleMoves(player).toMutableList()
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
        for (rule in rules) {
            possibleMoves.addAll(rule.getPossibleMoves(this, player))
        }
        return possibleMoves
    }

    fun filterForCheck(player: Player, possibleMoves: List<GameMove>): List<GameMove> {
        val res = mutableListOf<GameMove>()
        for (move in possibleMoves) {
            when (move) {
                is GameMove.BasicGameMove -> {
                    makeMove(move)
                    if (!inCheck(player)) {
                        res.add(move)
                    }
                    undoMove()
                }
                is GameMove.CompositeGameMove -> {
                    var valid = true
                    for (m in move.gameMoves) {
                        makeMove(m)
                        if (m.checkForCheck && inCheck(player)) {
                            valid = false
                        }
                    }
                    for (m in move.gameMoves) {
                        undoMove()
                    }
                    if (valid) {
                        res.add(move)
                    }
                }
            }
        }
        return res
    }

    fun inCheck(player: Player): Boolean {
        val kingCoordinate = board.getPieces(player).find { p -> p.first.player == player && p.first is King }!!.second
        return squareUnderAttack(kingCoordinate, player)
    }

    fun squareUnderAttack(coordinate: Coordinate, player: Player): Boolean {
        val nextPlayer = players[(players.indexOf(player) + 1) % 2]
        val moves = getPossibleMoves(nextPlayer)

        for (m in moves) {
            when (m) {
                is GameMove.BasicGameMove -> {
                    if (m.to.x == coordinate.x && m.to.y == coordinate.y) {
                        return true
                    }
                }
                is GameMove.CompositeGameMove -> {
                    for (move in m.gameMoves) {
                        if (move.to.x == coordinate.x && move.to.y == coordinate.y) {
                            return true
                        }
                    }
                }
            }
        }

        return false
    }

    fun undoMove() {
        if (moveLog.size == 0) {
            return
        }
        val gameMove = moveLog.removeAt(moveLog.size - 1)
        when (gameMove) {
            is GameMove.BasicGameMove -> {
                undoBasicMove(gameMove)
            }
            is GameMove.CompositeGameMove -> {
                for (move in gameMove.gameMoves.reversed()) {
                    undoBasicMove(move)
                }
            }
        }
    }

    private fun undoBasicMove(gameMove: GameMove.BasicGameMove) {
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
            possibleMoves.addAll(moveVisitor.visit(coordinate, piece, move, getCurrentPlayer()))
        }

        return possibleMoves
    }

    // override fun getHistory(): List<Pair<Board, GameMove>> {
    //     TODO("Not yet implemented")
    // }

    override fun makeMove(gameMove: GameMove) {
        when (gameMove) {
            is GameMove.BasicGameMove -> {
                makeBasicMove(gameMove)
            }
            is GameMove.CompositeGameMove -> {
                for (move in gameMove.gameMoves) {
                    makeBasicMove(move)
                }
            }
        }
        moveLog.add(gameMove)
    }

    private fun makeBasicMove(gameMove: GameMove.BasicGameMove) {
        board.removePiece(gameMove.from, gameMove.pieceMoved)
        if (gameMove.pieceCaptured != null) {
            board.removePiece(board.getPieceCoordinate(gameMove.pieceCaptured)!!, gameMove.pieceCaptured)
        }
        if (gameMove.piecePromotedTo != null) {
            board.addPiece(gameMove.to, gameMove.piecePromotedTo)
        } else {
            board.addPiece(gameMove.to, gameMove.pieceMoved)
        }
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

        if (move != null) {
            this.makeMove(move)
            nextPlayer()
        }
    }

    override fun nextPlayer() {
        playerTurn++
        playerTurn %= players.size
    }

    override fun getCurrentPlayer(): Player {
        return players[playerTurn]
    }

    override fun getNextPlayer(): Player {
        return players[(playerTurn + 1) % players.size]
    }

    override fun checkValidGame(): Boolean {

        if (players.size != NUM_PLAYERS) {
            print("Incorrect number of players")
            return false
        }
        return true
    }
}
