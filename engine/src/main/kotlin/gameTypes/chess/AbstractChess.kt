package gameTypes.chess

import coordinates.Coordinate2D
import gameMoves.GameMove2D
import gameMoves.GameMove2D.CompositeGameMove
import gameMoves.GameMove2D.SimpleGameMove
import gameMoves.GameMove2D.SimpleGameMove.*
import gameTypes.GameType
import gameTypes.chess.rules.SpecialRules
import pieces.King
import pieces.Piece2D
import players.Player

abstract class AbstractChess(val rules: List<SpecialRules<AbstractChess>> = listOf()) : GameType {
    override val players: List<Player> = listOf(Player(), Player())
    override var playerTurn: Int = 0

    override var seed: Double? = null

    override val moveLog: MutableList<GameMove2D> = mutableListOf()
    var stalemate = false
    var checkmate = false

    override val NUM_PLAYERS = 2

    override fun isOver(): Boolean {
        return checkmate || stalemate
    }

    override fun getValidMoves(player: Player): List<GameMove2D> {
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

    private fun getPossibleMoves(player: Player): List<GameMove2D> {
        val pieces = board.getPieces(player)
        val possibleMoves = mutableListOf<GameMove2D>()
        for (piece in pieces) {
            possibleMoves.addAll(getValidMoveForPiece(piece))
        }
        for (rule in rules) {
            rule.getPossibleMoves(this, player, possibleMoves)
        }
        return possibleMoves
    }

    private fun filterForCheck(player: Player, possibleMoves: List<GameMove2D>): List<GameMove2D> {
        val res = mutableListOf<GameMove2D>()
        for (move in possibleMoves) {
            when (move) {
                is SimpleGameMove -> {
                    makeMove(move)
                    if (!inCheck(player)) {
                        res.add(move)
                    }
                    undoMove()
                }
                is CompositeGameMove -> {
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

    override fun inCheck(player: Player): Boolean {
        val king = board.getPieces(player).find { p -> p.first.player == player && p.first is King } ?: return false
        val kingCoordinate = king.second
        return squareUnderAttack(kingCoordinate, player)
    }

    private fun squareUnderAttack(coordinate: Coordinate2D, player: Player): Boolean {
        val nextPlayer = players[(players.indexOf(player) + 1) % 2]
        val moves = getPossibleMoves(nextPlayer)

        for (m in moves) {
            when (m) {
                is BasicGameMove, is CompositeGameMove -> {
                    if (m.displayTo.x == coordinate.x && m.displayTo.y == coordinate.y) {
                        return true
                    }
                }
                is AddPiece, is RemovePiece -> {
                    return false
                }
            }
        }

        return false
    }

    override fun undoMove() {
        if (moveLog.size == 0) {
            return
        }
        undoMoveHelper(moveLog.removeAt(moveLog.size - 1))
    }

    private fun undoMoveHelper(gameMove: GameMove2D) {
        when (gameMove) {
            is BasicGameMove -> {
                undoBasicMove(gameMove)
            }
            is AddPiece -> {
                board.removePiece(gameMove.coordinate, gameMove.piece)
            }
            is RemovePiece -> {
                board.addPiece(gameMove.coordinate, gameMove.piece)
            }
            is CompositeGameMove -> {
                for (move in gameMove.gameMoves.reversed()) {
                    undoMoveHelper(move)
                }
            }
        }
    }

    private fun undoBasicMove(gameMove: BasicGameMove) {
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

    private fun getValidMoveForPiece(pair: Pair<Piece2D, Coordinate2D>): List<GameMove2D> {
        val possibleMoves = mutableListOf<GameMove2D>()
        // validate possible moves

        val piece = pair.first
        val coordinate = pair.second

        for (move in piece.moveTypes) {
            possibleMoves.addAll(move.generate(board, coordinate, piece, getCurrentPlayer()))
        }

        return possibleMoves
    }

    override fun makeMove(gameMove: GameMove2D) {
        when (gameMove) {
            is SimpleGameMove -> {
                makeSimpleMove(gameMove)
            }
            is CompositeGameMove -> {
                for (move in gameMove.gameMoves) {
                    makeSimpleMove(move)
                }
            }
        }
        moveLog.add(gameMove)
    }

    private fun makeSimpleMove(gameMove: SimpleGameMove) {
        when (gameMove) {
            is BasicGameMove -> {
                makeBasicMove(gameMove)
            }
            is AddPiece -> {
                makeAddPieceMove(gameMove)
            }
            is RemovePiece -> {
                makeRemovePieceMove(gameMove)
            }
        }
    }

    private fun makeBasicMove(gameMove: BasicGameMove) {
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

    private fun makeAddPieceMove(gameMove: AddPiece) {
        board.addPiece(gameMove.coordinate, gameMove.piece)
    }

    private fun makeRemovePieceMove(gameMove: RemovePiece) {
        board.removePiece(gameMove.coordinate, gameMove.piece)
    }
}
